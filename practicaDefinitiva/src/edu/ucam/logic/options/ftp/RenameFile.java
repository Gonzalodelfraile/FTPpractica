package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.io.IOException;
import java.util.Scanner;

public class RenameFile implements Option {
    private final FtpClient ftpClient;

    public RenameFile(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo a renombrar: ");
        String oldName = scanner.nextLine();
        System.out.println("Introduce el nuevo nombre del archivo: ");
        String newName = scanner.nextLine();

        try {
            ftpClient.rename( oldName, newName);
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
        return "Renombrar archivo";
    }
}
