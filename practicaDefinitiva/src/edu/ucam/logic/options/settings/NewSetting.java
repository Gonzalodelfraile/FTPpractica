package edu.ucam.logic.options.settings;

import edu.ucam.logic.ftp.FtpClient;
import edu.ucam.models.FtpConfig;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewSetting implements Option {

    private User user;

    public NewSetting(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Creando nueva configuración...");
        FtpConfig ftpConfig = view.getFtpData();

        if((new FtpClient(ftpConfig)).testConnection()) {
            user.addConfig(ftpConfig);
            view.display("Configuración añadida correctamente");
        } else {
            view.displayError("No se ha podido conectar con el servidor FTP");
        }
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Nueva configuración FTP";
    }
}