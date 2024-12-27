package edu.ucam.logic.menus;

import edu.ucam.logic.options.*;
import edu.ucam.logic.options.admin.DeleteUser;
import edu.ucam.logic.options.admin.ListUsers;
import edu.ucam.logic.options.admin.NewUser;
import edu.ucam.models.Menu;
import edu.ucam.models.User;


public class AdminMenu extends Menu {


    public AdminMenu(User user) {
        super("Menu de Administrador");

        addOption(1, new NewUser());
        addOption(2, new DeleteUser(user));
        addOption(3, new ListUsers());
        addOption(4, new LogOut());
        addOption(5, new Exit());
    }


}
