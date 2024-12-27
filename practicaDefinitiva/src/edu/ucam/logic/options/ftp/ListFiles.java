package edu.ucam.logic.options.ftp;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class ListFiles implements Option {

    private FtpClient ftpClient;

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
            // Enviar comando PASV y obtener la respuesta
            String pasvResponse = ftpClient.sendCommand("PASV");
            Log.getInstance().debug("Respuesta PASV: " + pasvResponse);

            // Leer el listado de archivos desde el canal de datos
            String fileList = ftpClient.readDataFromPasv(pasvResponse, "LIST");
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
