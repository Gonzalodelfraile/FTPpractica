package edu.ucam.logic.options.user;

import edu.ucam.logic.menus.FtpClientMenu;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

public class OpenFtpClient implements Option {
    private User user;

    public OpenFtpClient(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Abriendo cliente FTP...");
        new FtpClientMenu(user).displayMenu();
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Abrir cliente FTP";
    }
}
