package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;

public class CreateFolder implements Option {
    private final FtpClient ftpClient;

    public CreateFolder(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        String folderName = view.getInput("Nombre de la carpeta a crear: ");

        try {
            ftpClient.createFolder(folderName);
        } catch (IOException e) {
            Log.getInstance().error( e.getMessage());
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Crear carpeta";
    }
}
