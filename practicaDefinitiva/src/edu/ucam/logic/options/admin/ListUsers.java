package edu.ucam.logic.options.admin;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.ui.ViewFactory;
import edu.ucam.utils.Log;

public class ListUsers implements Option {
    @Override
    public void execute() {
        Log.getInstance().debug("Listando usuarios...");
        String userList = UserManager.getInstance().listUsers();
        view.display(userList);

    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Listar usuarios";
    }
}
