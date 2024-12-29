package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;

public class ChangeDirectory implements Option {
    private final FtpClient ftpClient;

    public ChangeDirectory(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        String directory = view.getInput("Introduce el directorio al que quieres cambiar:");

        try {
            ftpClient.changeDirectory(directory);
        } catch (IOException e) {
            view.displayError( e.getMessage());
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
