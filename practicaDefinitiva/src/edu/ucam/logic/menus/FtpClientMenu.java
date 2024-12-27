package edu.ucam.logic.menus;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.logic.options.Back;
import edu.ucam.logic.options.Exit;
import edu.ucam.logic.options.LogOut;
import edu.ucam.logic.options.ftp.ListFiles;
import edu.ucam.models.Menu;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

public class FtpClientMenu extends Menu {
    private FtpClient ftpClient;

    public FtpClientMenu(User user) {
        super("Cliente FTP:");

        startFtpClient(user);

        addOption(1, new LogOut());
        addOption(2, new Exit());

        if (ftpClient != null) {
            addOption(3, new ListFiles(ftpClient));
            //addOption(4, new UploadFile(ftpClient));
//            addOption(5, new DownloadFile(ftpClient));
//            addOption(6, new CreateFolder(ftpClient));
//            addOption(7, new DeleteFolder(ftpClient));
//            addOption(8, new RenameFile(ftpClient));


        } else {
            Log.getInstance().error("No se pudo iniciar el cliente FTP.");
        }

    }

    @Override
    public void displayMenu() {
        try {
            super.displayMenu();
        } finally {
            // Asegurarse de desconectar al salir del menú
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        }
    }

    private void startFtpClient(User user) {
        if (user.getActiveConfig() != null) {
            ftpClient = new FtpClient(user.getActiveConfig());
            if (!ftpClient.connect()) {
                Log.getInstance().error("No se pudo conectar al servidor FTP con la configuración activa.");
                ftpClient = null;
            }
        } else {
            Log.getInstance().error("No hay configuración FTP activa para este usuario.");
        }
    }
}
