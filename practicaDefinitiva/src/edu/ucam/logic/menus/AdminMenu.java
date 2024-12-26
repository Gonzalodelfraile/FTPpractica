package edu.ucam.logic.menus;

import edu.ucam.logic.options.*;
import edu.ucam.models.Menu;
import edu.ucam.models.User;

public class AdminMenu extends Menu {

    private User user;
    public AdminMenu(User user) {
        super("Menu de administrador");
        this.user = user;

        addOption(1, new NewUser());
        addOption(2, new DeleteUser());
        addOption(3, new ListUsers());
        addOption(4, new LogOut());
        addOption(5, new Exit());
    }


}
