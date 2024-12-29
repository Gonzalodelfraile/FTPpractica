package edu.ucam.logic.ftp;

import edu.ucam.models.FtpConfig;
import edu.ucam.ui.View;
import edu.ucam.ui.ViewFactory;
import edu.ucam.utils.Log;

import java.io.*;
import java.net.Socket;

public class FtpClient {

    private static final String RESPONSE_USER_OK = "331"; // Usuario correcto
    private static final String RESPONSE_AUTH_SUCCESS = "230"; // Autenticación correcta
    private static final String RESPONSE_READY_FOR_TRANSFER = "150"; // Listo para la transferencia
    private static final String RESPONSE_TRANSFER_COMPLETE = "226"; // Transferencia completada
    private static final String RESPONSE_INITIAL_OK = "220"; // Servicio listo
    private static final String RESPONSE_DIRECTORY_CHANGED = "250"; // Directorio cambiado correctamente
    private static final String RESPONSE_DIRECTORY_CREATED = "257"; // Directorio creado correctamente
    private static final String RESPONSE_RENAME_READY = "350"; // Listo para renombrar

    private static final int BUFFER_SIZE = 4096; // Tamaño del buffer para transferencia de datos

    private final View view;
    private final FtpConfig config;
    private Socket controlSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public FtpClient(FtpConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("La configuración FTP no puede ser nula.");
        }

        this.config = config;
        this.view = ViewFactory.getView();
    }

    public boolean connect() {
        try {
            Log.getInstance().debug("Conectando al servidor FTP...");
            controlSocket = new Socket(config.getServerIP(), config.getPort());
            initializeStreams();

            if (!validateInitialResponse() || !authenticate()) {
                return false;
            }

            view.display("Conexión establecida");
            return true;
        } catch (IOException e) {
            view.displayError("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (controlSocket != null && !controlSocket.isClosed()) {
                sendCommand("QUIT");
                controlSocket.close();
                view.display("Desconectado del servidor FTP.");
            }
        } catch (IOException e) {
            view.displayError("Error al desconectar: " + e.getMessage());
        }
    }

    public String sendCommand(String command) throws IOException {
        writer.println(command);
        return readControlResponse();
    }

    private String readControlResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
            if (line.matches("^\\d{3} .*$")) {
                break;
            }
        }
        return response.toString().trim();
    }

    public void uploadFile(String localPath, String fileName) throws IOException {

        File file = validateFile(localPath);

        try (Socket dataSocket = openDataSocket("STOR " + fileName);
             BufferedOutputStream dataOut = new BufferedOutputStream(dataSocket.getOutputStream());
             FileInputStream fileIn = new FileInputStream(file)) {

            Log.getInstance().debug("Subiendo archivo: " + localPath + " a " + fileName);
            transferData(fileIn, dataOut);
            confirmTransferComplete();
            view.display("Archivo subido como: " + fileName);
        }
    }

    public void downloadFile(String fileName, String localPath) throws IOException {
        File localFile = new File(localPath, fileName);
        if (localFile.isDirectory()) {
            throw new IOException("La ruta local no puede ser un directorio: " + localPath);
        }
        if (!localFile.getParentFile().exists() && !localFile.getParentFile().mkdirs()) {
            throw new IOException("No se pudo crear el directorio para el archivo: " + localPath);
        }

        try (Socket dataSocket = openDataSocket("RETR " + fileName);
             BufferedInputStream dataIn = new BufferedInputStream(dataSocket.getInputStream());
             FileOutputStream fileOut = new FileOutputStream(localFile)) {

            Log.getInstance().debug("Descargando archivo: " + fileName + " a " + localPath);
            transferData(dataIn, fileOut);
            confirmTransferComplete();
            view.display("Archivo descargado correctamente a: " + localPath);
        }
    }

    private void initializeStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
        writer = new PrintWriter(controlSocket.getOutputStream(), true);
    }

    private boolean validateInitialResponse() throws IOException {
        String response = readControlResponse();
        Log.getInstance().debug("Respuesta inicial: " + response);
        return response.startsWith(RESPONSE_INITIAL_OK);
    }

    private boolean authenticate() throws IOException {
        sendAndValidate("USER " + config.getUser(), RESPONSE_USER_OK);
        sendAndValidate("PASS " + config.getPassword(), RESPONSE_AUTH_SUCCESS);
        return true;
    }

    private void sendAndValidate(String command, String expectedCode) throws IOException {
        String response = sendCommand(command);
        if (!response.startsWith(expectedCode)) {
            throw new IOException("Respuesta inesperada: " + response);
        }
    }

    private Socket openDataSocket(String command) throws IOException {
        String pasvResponse = sendCommand("PASV");
        String[] ipAndPort = parsePasvResponse(pasvResponse);
        Socket dataSocket = new Socket(ipAndPort[0], Integer.parseInt(ipAndPort[1]));

        sendAndValidate(command, RESPONSE_READY_FOR_TRANSFER);
        return dataSocket;
    }

    private void transferData(InputStream in, OutputStream out) throws IOException {
        Log.getInstance().debug("Transfiriendo datos...");
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            view.display("Transferencia completada");
        } catch (IOException e) {
            view.displayError("Error al transferir datos: " + e.getMessage());
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                Log.getInstance().warning("No se pudo cerrar el InputStream: " + e.getMessage());
            }
            try {
                out.close();
            } catch (IOException e) {
                Log.getInstance().warning("No se pudo cerrar el OutputStream: " + e.getMessage());
            }
        }
    }

    private void confirmTransferComplete() throws IOException {
        String response = readControlResponse();
        if (!response.startsWith(RESPONSE_TRANSFER_COMPLETE)) {
            throw new IOException("Error al completar la transferencia: " + response);
        }
    }

    private String[] parsePasvResponse(String response) {
        String ipAndPort = response.substring(response.indexOf('(') + 1, response.indexOf(')'));
        String[] parts = ipAndPort.split(",");
        String ip = String.join(".", parts[0], parts[1], parts[2], parts[3]);
        int port = (Integer.parseInt(parts[4]) << 8) + Integer.parseInt(parts[5]);
        return new String[]{ip, String.valueOf(port)};
    }

    private File validateFile(String path) throws FileNotFoundException {
        Log.getInstance().debug("Validando archivo: " + path);
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Archivo no encontrado: " + path);
        }
        return file;
    }

    public String listFiles(String directory) throws IOException {
        String command = "LIST " + (directory != null ? directory : "");
        try (Socket dataSocket = openDataSocket(command);
             BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()))) {

            StringBuilder fileList = new StringBuilder();
            String line;
            while ((line = dataReader.readLine()) != null) {
                fileList.append(line).append("\n");
            }

            confirmTransferComplete();
            return fileList.toString().trim();
        }
    }

    public void changeDirectory(String path) throws IOException {
        String command = "CWD " + path;
        String response = sendCommand(command);

        if (!response.startsWith(RESPONSE_DIRECTORY_CHANGED)) {
            throw new IOException("Error al cambiar de directorio: " + response);
        }

        view.display("Directorio cambiado a: " + path);
    }

    public String getCurrentDirectory() throws IOException {
        String response = sendCommand("PWD");
        if (response.startsWith(RESPONSE_DIRECTORY_CREATED)) {
            return response;
        } else {
            throw new IOException(response);
        }
    }

    public void createFolder(String directoryName) throws IOException {
        if (directoryName == null || directoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del directorio no puede estar vacío.");
        }

        String response = sendCommand("MKD " + directoryName);
        if (response.startsWith(RESPONSE_DIRECTORY_CREATED)) {
            view.display("Directorio creado: " + directoryName);
        } else {
            throw new IOException("Error al crear el directorio: " + response);
        }
    }

    public void deleteFolder(String directoryName) throws IOException {
        if (directoryName == null || directoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del directorio no puede estar vacío.");
        }

        String response = sendCommand("RMD " + directoryName);
        if (response.startsWith(RESPONSE_DIRECTORY_CHANGED)) {
            view.display("Directorio eliminado: " + directoryName);
        } else {
            throw new IOException("Error al eliminar el directorio: " + response);
        }
    }

    public void rename(String oldName, String newName) throws IOException {
        if (oldName == null || oldName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre original no puede estar vacío.");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacío.");
        }

        String response = sendCommand("RNFR " + oldName);
        if (!response.startsWith(RESPONSE_RENAME_READY)) {
            throw new IOException("Error al iniciar el renombrado: " + response);
        }

        response = sendCommand("RNTO " + newName);
        if (!response.startsWith(RESPONSE_DIRECTORY_CHANGED)) {
            throw new IOException("Error al renombrar: " + response);
        }
        view.display("Renombrado: " + oldName + " a " + newName);
    }
}
