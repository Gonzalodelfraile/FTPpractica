package edu.ucam.logic.menus;

import edu.ucam.logic.options.*;

import edu.ucam.logic.options.user.OpenFTPSettings;
import edu.ucam.models.Menu;

public class UserMenu extends Menu {

    public UserMenu() {
        super("Menu de Usuario");
        addOption(1, new OpenFTPSettings());
        //addOption(2, new OpenFTPClient());

        addOption(4, new LogOut());
        addOption(5, new Exit());


    }
}
