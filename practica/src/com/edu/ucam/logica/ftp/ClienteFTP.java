package com.edu.ucam.logica.ftp;

import java.io.BufferedReader;
import java.io.*;
import java.net.Socket;

public class ClienteFTP {
    private Socket controlSocket;
    private BufferedReader br;
    private PrintWriter pw;

    public void connect(String server, int port, String user, String password) throws IOException {
        controlSocket = new Socket(server, port);
        br = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
        pw = new PrintWriter(new OutputStreamWriter(controlSocket.getOutputStream()),true);

        // Leer respuesta inicial del servidor
        readResponse();

        // Enviar usuario
        sendCommand("USER " + user);
        readResponse();

        // Enviar contraseña
        sendCommand("PASS " + password);
        readResponse();
    }

    public void disconnect() throws IOException {
        sendCommand("QUIT");
        readResponse();
        controlSocket.close();
    }

    public void listFiles() throws IOException {
        sendCommand("PASV");
        String response = readResponse();

        // Obtener datos de conexión pasiva
        String[] parts = response.split("\\(")[1].split("\\)")[0].split(",");
        String dataHost = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
        int dataPort = Integer.parseInt(parts[4]) * 256 + Integer.parseInt(parts[5]);

        Socket dataSocket = new Socket(dataHost, dataPort);

        sendCommand("LIST");
        readResponse();

        BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
        String line;
        while ((line = dataReader.readLine()) != null) {
            System.out.println(line);
        }
        dataSocket.close();
        readResponse();
    }

    public void changeDirectory(String directory) throws IOException {
        sendCommand("CWD " + directory);
        readResponse();
    }

    public void createDirectory(String directory) throws IOException {
        sendCommand("MKD " + directory);
        readResponse();
    }

    public void deleteDirectory(String directory) throws IOException {
        sendCommand("RMD " + directory);
        readResponse();
    }

    public void uploadFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        sendCommand("PASV");
        String response = readResponse();

        String[] parts = response.split("\\(")[1].split("\\)")[0].split(",");
        String dataHost = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
        int dataPort = Integer.parseInt(parts[4]) * 256 + Integer.parseInt(parts[5]);

        Socket dataSocket = new Socket(dataHost, dataPort);

        sendCommand("STOR " + file.getName());
        readResponse();

        OutputStream dataOut = dataSocket.getOutputStream();
        FileInputStream fileInput = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInput.read(buffer)) != -1) {
            dataOut.write(buffer, 0, bytesRead);
        }

        fileInput.close();
        dataOut.close();
        dataSocket.close();

        readResponse();
    }

    public void downloadFile(String fileName) throws IOException {
        sendCommand("PASV");
        String response = readResponse();

        String[] parts = response.split("\\(")[1].split("\\)")[0].split(",");
        String dataHost = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
        int dataPort = Integer.parseInt(parts[4]) * 256 + Integer.parseInt(parts[5]);

        Socket dataSocket = new Socket(dataHost, dataPort);

        sendCommand("RETR " + fileName);
        readResponse();

        InputStream dataIn = dataSocket.getInputStream();
        FileOutputStream fileOut = new FileOutputStream(fileName);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = dataIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
        }

        fileOut.close();
        dataIn.close();
        dataSocket.close();

        readResponse();
    }

    public void renameFile(String oldName, String newName) throws IOException {
        sendCommand("RNFR " + oldName);
        readResponse();
        sendCommand("RNTO " + newName);
        readResponse();
    }

    private void sendCommand(String command) throws IOException {
        pw.write(command + "\r\n");
        pw.flush();
    }

    private String readResponse() throws IOException {
        String response = br.readLine();
        System.out.println("Respuesta del servidor: " + response);
        return response;
    }
}
