package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;
import java.util.Scanner;

public class DeleteFolder implements Option {
    private final FtpClient ftpClient;
    public DeleteFolder(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre de la carpeta a borrar: ");
        String folderName = scanner.nextLine();

        try {
            ftpClient.deleteFolder( folderName );
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
        return "Eliminar carpeta";
    }
}
