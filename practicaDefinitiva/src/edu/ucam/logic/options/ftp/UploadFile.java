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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la ruta del archivo a subir: ");
        String filePath = scanner.nextLine();

        System.out.println("Introduce el nombre: ");
        String fileName = scanner.nextLine();

        try {
            ftpClient.uploadFile(filePath, fileName);
        } catch (IOException e) {
            Log.getInstance().error("Error al subir archivo: " + e.getMessage());
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
