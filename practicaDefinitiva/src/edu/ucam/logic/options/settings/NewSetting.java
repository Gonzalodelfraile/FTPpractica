package edu.ucam.logic.options.settings;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.FtpConfig;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewSetting implements Option {

    private User user;

    public NewSetting(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Creando nueva configuración...");
        FtpConfig ftpConfig = ftpDataInput();

        if (testFtpConnection(ftpConfig)) {
            Log.getInstance().info("Se ha establecido la conexión.");
            // TODO la configuración a la lista del usuario
            user.addConfig(ftpConfig);
        } else {
            Log.getInstance().error("Error de conexión.");
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
        FtpClient ftpClient = new FtpClient(config);
        if (ftpClient.connect()) {
            Log.getInstance().info("Conexión exitosa.");
            ftpClient.disconnect();
            return true;
        } else {
            Log.getInstance().error("Error al conectar.");
            ftpClient.disconnect();
            return false;
        }

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