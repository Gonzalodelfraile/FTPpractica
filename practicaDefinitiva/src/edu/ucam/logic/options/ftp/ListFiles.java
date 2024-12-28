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
        if (ftpClient == null) {
            Log.getInstance().error("No hay una conexión FTP activa.");
            return;
        }

        try {

            String fileList = ftpClient.listFiles("/");
            System.out.println("Archivos en el directorio raíz:");
            System.out.println(fileList);
            

        } catch (Exception e) {
            Log.getInstance().error("Error al listar archivos: " + e.getMessage());
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
