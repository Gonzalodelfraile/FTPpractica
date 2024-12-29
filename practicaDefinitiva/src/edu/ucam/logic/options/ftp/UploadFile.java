package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;
import java.util.Scanner;

public class UploadFile implements Option {

    private final FtpClient ftpClient;

    public UploadFile(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        String filePath = view.getInput("Ruta del archivo a subir: ");
        String fileName = view.getInput("Nombre del archivo a subir: ");

        try {
            ftpClient.uploadFile(filePath, fileName);
        } catch (IOException e) {
            view.displayError("Error al subir archivo: " + e.getMessage());
        }

    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Subir archivo";
    }
}
