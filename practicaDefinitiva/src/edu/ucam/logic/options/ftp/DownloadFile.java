package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;
import java.util.Scanner;

public class DownloadFile implements Option {
    private final FtpClient ftpClient;

    public DownloadFile(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {

        String fileName = view.getInput("Nombre del archivo a descargar: ");
        String destinationPath = view.getInput("Ruta de destino: ");

        try {
            ftpClient.downloadFile( fileName, destinationPath);
        } catch (IOException e) {
            view.displayError("Error al descargar archivo: " + e.getMessage());
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Descargar archivo";
    }
}
