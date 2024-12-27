package edu.ucam.logic.options.user;

import edu.ucam.logic.menus.FtpSettingsMenu;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

public class OpenFTPSettings implements Option {
    private User user;

    public OpenFTPSettings(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Abriendo settings de FTP...");
        new FtpSettingsMenu(user).displayMenu();
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Configuración FTP";
    }
}
