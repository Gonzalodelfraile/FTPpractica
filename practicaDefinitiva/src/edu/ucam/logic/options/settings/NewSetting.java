package edu.ucam.logic.options.settings;

import edu.ucam.models.FtpConfig;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewSetting implements Option {

    @Override
    public void execute() {
        Log.getInstance().debug("Creando nueva configuración...");
        FtpConfig ftpConfig = ftpDataInput();

        if (testFtpConnection(ftpConfig)) {
            Log.getInstance().info("Conexión exitosa. Configuración válida.");
            // TODO la configuración a la lista del usuario
            // Ejemplo: user.addConfig(ftpConfig);
        } else {
            Log.getInstance().error("Error de conexión. Configuración no válida.");
        }
    }

    private FtpConfig ftpDataInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre de la configuración: ");
        String name = scanner.nextLine();
        System.out.println("Introduce el host: ");
        String host = scanner.nextLine();
        System.out.println("Introduce el usuario: ");
        String user = scanner.nextLine();
        System.out.println("Introduce la contraseña: ");
        String password = scanner.nextLine();

        return new FtpConfig(name, host, user, password);
    }

    private boolean testFtpConnection(FtpConfig config) {
        try (Socket socket = new Socket(config.getServerIP(), config.getPort())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Leer respuesta inicial del servidor
            String response = readFtpResponse(reader);
            Log.getInstance().debug("Respuesta inicial del servidor: " + response);

            // Enviar comando USER
            writer.println("USER " + config.getUser());
            response = readFtpResponse(reader);
            Log.getInstance().debug("Respuesta al comando USER: " + response);

            // Validar si el servidor pide la contraseña
            if (!response.startsWith("331")) {
                Log.getInstance().error("Error de conexión: Usuario no aceptado.");
                return false;
            }

            // Enviar comando PASS
            writer.println("PASS " + config.getPassword());
            response = readFtpResponse(reader);
            Log.getInstance().debug("Respuesta al comando PASS: " + response);

            // Validar autenticación
            if (!response.startsWith("230")) {
                Log.getInstance().error("Error de conexión: Contraseña incorrecta o autenticación fallida.");
                return false;
            }

            Log.getInstance().info("Autenticación al servidor FTP exitosa.");
            return true;
        } catch (Exception e) {
            Log.getInstance().error("Error de conexión. Configuración no válida: " + e.getMessage());
            return false;
        }
    }

    //leer respuestas completas del servidor (manejo de múltiples líneas)
    private String readFtpResponse(BufferedReader reader) throws IOException {
        StringBuilder fullResponse = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            fullResponse.append(line).append("\n");
            // Si la línea no comienza con un código numérico seguido de un guión, es la última línea
            if (line.matches("^\\d{3} .*")) {
                break;
            }
        }

        return fullResponse.toString().trim();
    }



    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Nueva configuración FTP";
    }
}