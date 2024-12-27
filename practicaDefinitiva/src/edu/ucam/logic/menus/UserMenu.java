package edu.ucam.logic.menus;


import edu.ucam.logic.options.*;

import edu.ucam.logic.options.user.OpenFTPSettings;
import edu.ucam.logic.options.user.OpenFtpClient;
import edu.ucam.models.Menu;
import edu.ucam.models.User;

public class UserMenu extends Menu {


    public UserMenu(User user) {
        super("Menu de Usuario");


        addOption(1, new OpenFTPSettings(user));
        addOption(2, new OpenFtpClient(user));

        addOption(4, new LogOut());
        addOption(5, new Exit());


    }

}
