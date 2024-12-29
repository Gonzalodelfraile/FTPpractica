package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;

public class ShowCurrentDirectory implements Option {
    private final FtpClient ftpClient;
    public ShowCurrentDirectory(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Mostrando directorio actual...");
        try {
            String currentDirectory = ftpClient.getCurrentDirectory();
            view.display("Directorio actual: " + currentDirectory);
        } catch (IOException e) {
            view.displayError("Error al mostrar el directorio actual: " + e.getMessage());
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
