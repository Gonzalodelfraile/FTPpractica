package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;
import java.util.Scanner;

public class ChangeDirectory implements Option {
    private final FtpClient ftpClient;

    public ChangeDirectory(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el directorio al que quieres cambiar:");
        String directory = scanner.nextLine();

        try {
            ftpClient.changeDirectory(directory);
            Log.getInstance().info("Directorio cambiado a " + directory);

        } catch (IOException e) {
            Log.getInstance().error(e.getMessage());
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Cambiar directorio";
    }
}
