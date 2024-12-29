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
        String oldName = view.getInput("Nombre del archivo a renombrar: ");
        String newName = view.getInput("Nuevo nombre: ");

        try {
            ftpClient.rename( oldName, newName);
        } catch (IOException e) {
            view.displayError(e.getMessage());
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
