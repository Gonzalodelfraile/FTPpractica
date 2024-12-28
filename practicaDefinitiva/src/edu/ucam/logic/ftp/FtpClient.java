package edu.ucam.logic.ftp;

import edu.ucam.models.FtpConfig;
import edu.ucam.utils.Log;

import java.io.*;
import java.net.Socket;

public class FtpClient {

    private static final String RESPONSE_USER_OK = "331";
    private static final String RESPONSE_AUTH_SUCCESS = "230";
    private static final String RESPONSE_READY_FOR_TRANSFER = "150";
    private static final String RESPONSE_TRANSFER_COMPLETE = "226";

    private final FtpConfig config;
    private Socket controlSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public FtpClient(FtpConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("La configuración FTP no puede ser nula.");
        }
        this.config = config;
    }

    public boolean connect() {
        try {
            Log.getInstance().debug("Conectando al servidor FTP...");
            controlSocket = new Socket(config.getServerIP(), config.getPort());
            initializeStreams();

            if (!validateInitialResponse() || !authenticate()) {
                return false;
            }

            Log.getInstance().info("Conexión exitosa al servidor FTP.");
            return true;
        } catch (IOException e) {
            Log.getInstance().error("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (controlSocket != null && !controlSocket.isClosed()) {
                sendCommand("QUIT");
                controlSocket.close();
                Log.getInstance().info("Desconectado del servidor FTP.");
            }
        } catch (IOException e) {
            Log.getInstance().error("Error al desconectar: " + e.getMessage());
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
            if (line.matches("^\\d{3} .*")) {
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
            Log.getInstance().info("Archivo subido como: " + fileName);
        }
    }

    public void downloadFile(String fileName, String localPath) throws IOException {
        // Validar que el archivo remoto y el destino local son válidos
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

            // Transferir datos del canal de datos al archivo local
            transferData(dataIn, fileOut);

            // Confirmar que la transferencia fue completada
            confirmTransferComplete();
            Log.getInstance().info("Archivo descargado correctamente a: " + localPath);

        } catch (IOException e) {
            Log.getInstance().error("Error al descargar archivo: " + e.getMessage());
            throw e; // Re-lanzar para permitir el manejo en niveles superiores
        }
    }


    private void initializeStreams() throws IOException {
        reader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
        writer = new PrintWriter(controlSocket.getOutputStream(), true);
    }

    private boolean validateInitialResponse() throws IOException {
        String response = readControlResponse();
        Log.getInstance().debug("Respuesta inicial: " + response);
        return response.startsWith("220"); // 220: Service ready
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
        byte[] buffer = new byte[4096]; // Tamaño típico del buffer
        int bytesRead;

        try {
            while ((bytesRead = in.read(buffer)) != -1) { // Lee hasta que no haya más datos
                out.write(buffer, 0, bytesRead); // Escribe los datos leídos
            }
            out.flush(); // confirma que todos los datos se han escrito
            Log.getInstance().info("Transferencia de datos completada.");
        } catch (IOException e) {
            Log.getInstance().error("Error durante la transferencia de datos: " + e.getMessage());
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                Log.getInstance().warning("No se pudo cerrar el InputStream: " + e.getMessage());
            }
            try {
                out.close(); // Asegúrate de cerrar el OutputStream
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

        if (!response.startsWith("250")) {
            throw new IOException("Error al cambiar de directorio: " + response);
        }
    }

    public String getCurrentDirectory() throws IOException {
        String response = sendCommand("PWD");
        if (response.startsWith("257")) {
            return response;
            //return response.substring(4,response.indexOf('"')); // Eliminar el código de respuesta ("257 ")
        } else {
            throw new IOException("Error al obtener el directorio actual: " + response);
        }
    }

    public void createFolder(String directoryName) throws IOException {
        if (directoryName == null || directoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del directorio no puede estar vacío.");
        }

        String response = sendCommand("MKD " + directoryName);
        if (response.startsWith("257")) { // Respuesta esperada: 257 "directorio creado"
            Log.getInstance().info("Directorio creado: " + directoryName);
        } else {
            throw new IOException("Error al crear el directorio: " + response);
        }
    }

    public void deleteFolder(String directoryName) throws IOException {
        if (directoryName == null || directoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del directorio no puede estar vacío.");
        }

        String response = sendCommand("RMD " + directoryName);
        if (response.startsWith("250")) { // Respuesta esperada: 250 "directorio eliminado"
            Log.getInstance().info("Directorio eliminado: " + directoryName);
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

        // Enviar comando RNFR (Rename From)
        String response = sendCommand("RNFR " + oldName);
        if (!response.startsWith("350")) { // Respuesta esperada: 350 "File or directory exists, ready to rename"
            throw new IOException("Error al iniciar el renombrado: " + response);
        }

        // Enviar comando RNTO (Rename To)
        response = sendCommand("RNTO " + newName);
        if (!response.startsWith("250")) { // Respuesta esperada: 250 "Rename successful"
            throw new IOException("Error al renombrar: " + response);
        }

        Log.getInstance().info("Renombrado: " + oldName + " a " + newName);
    }


}