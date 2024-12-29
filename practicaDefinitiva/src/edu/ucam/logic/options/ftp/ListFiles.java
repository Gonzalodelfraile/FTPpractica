package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class ListFiles implements Option {

    private final FtpClient ftpClient;

    public ListFiles(FtpClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    @Override
    public void execute() {
        try {
            String fileList = ftpClient.listFiles("/");
            view.display("Archivos en el directorio raíz:");
            view.display(fileList);
        } catch (Exception e) {
            view.displayError("Error al listar archivos: " + e.getMessage());
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Listar archivos del directorio raíz";
    }
}
