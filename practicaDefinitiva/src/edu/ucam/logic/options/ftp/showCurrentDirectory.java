package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;

public class showCurrentDirectory implements Option {
    private final FtpClient ftpClient;
    public showCurrentDirectory(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Mostrando directorio actual...");
        try {
            String currentDirectory = ftpClient.getCurrentDirectory();
            System.out.println("Directorio actual: " + currentDirectory);
        } catch (IOException e) {
            Log.getInstance().error("Error al mostrar el directorio actual: " + e.getMessage());
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Mostrar directorio actual";
    }
}
