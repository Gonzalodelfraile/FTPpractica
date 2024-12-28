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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo a descargar: ");
        String fileName = scanner.nextLine();

        System.out.println("Introduce la ruta de destino: ");
        String destinationPath = scanner.nextLine();

        try {
            ftpClient.downloadFile( fileName, destinationPath);
        } catch (IOException e) {
            Log.getInstance().error("Error al descargar archivo: " + e.getMessage());
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
