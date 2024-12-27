package edu.ucam.logic.ftp;

import edu.ucam.models.FtpConfig;
import edu.ucam.utils.Log;

import java.io.*;
import java.net.Socket;

public class FtpClient {
    private FtpConfig config;
    private Socket controlSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public FtpClient(FtpConfig config) {
        this.config = config;
    }

    public boolean connect() {
        try {
            Log.getInstance().debug("Conectando al servidor FTP...");
            controlSocket = new Socket(config.getServerIP(), config.getPort());
            reader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            writer = new PrintWriter(controlSocket.getOutputStream(), true);

            // Leer respuesta inicial
            String response = readResponse();
            Log.getInstance().debug("Respuesta inicial: " + response);

            // Enviar comando USER
            writer.println("USER " + config.getUser());
            response = readResponse();
            if (!response.startsWith("331")) {
                Log.getInstance().error("Usuario no aceptado.");
                return false;
            }

            // Enviar comando PASS
            writer.println("PASS " + config.getPassword());
            response = readResponse();
            if (!response.startsWith("230")) {
                Log.getInstance().error("Autenticación fallida.");
                return false;
            }

            Log.getInstance().info("Autenticación exitosa.");
            return true;
        } catch (Exception e) {
            Log.getInstance().error("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (controlSocket != null && !controlSocket.isClosed()) {
                writer.println("QUIT");
                controlSocket.close();
                Log.getInstance().info("Desconectado del servidor FTP.");
            }
        } catch (IOException e) {
            Log.getInstance().error("Error al desconectar: " + e.getMessage());
        }
    }

    public String sendCommand(String command) throws IOException {
        writer.println(command);
        return readResponse();
    }

    private String readResponse() throws IOException {
        StringBuilder fullResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            fullResponse.append(line).append("\n");
            if (line.matches("^\\d{3} .*")) {
                break;
            }
        }
        return fullResponse.toString().trim();
    }

    public String readDataFromPasv(String pasvResponse, String dataCommand) throws IOException {
        // Extraer IP y puerto del comando PASV
        String[] pasvData = parsePasvResponse(pasvResponse);
        String ip = pasvData[0];
        int port = Integer.parseInt(pasvData[1]);

        // Abrir conexión al canal de datos
        try (Socket dataSocket = new Socket(ip, port);
             BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()))) {

            // Enviar comando al canal de control
            writer.println(dataCommand);
            String controlResponse = readResponse();
            Log.getInstance().debug("Respuesta al comando " + dataCommand + ": " + controlResponse);

            // Leer datos del canal de datos
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = dataReader.readLine()) != null) {
                data.append(line).append("\n");
            }

            return data.toString().trim();
        }
    }

    private String[] parsePasvResponse(String response) {
        // Extraer la IP y el puerto del comando PASV
        String ipAndPort = response.substring(response.indexOf('(') + 1, response.indexOf(')'));
        String[] parts = ipAndPort.split(",");
        String ip = String.join(".", parts[0], parts[1], parts[2], parts[3]);
        int port = (Integer.parseInt(parts[4]) << 8) + Integer.parseInt(parts[5]);
        return new String[]{ip, String.valueOf(port)};
    }
}

