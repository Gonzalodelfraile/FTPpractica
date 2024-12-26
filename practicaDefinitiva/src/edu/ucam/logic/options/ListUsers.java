package edu.ucam.logic.options;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class ListUsers implements Option {
    @Override
    public void execute() {
        Log.getInstance().debug("Listando usuarios...");
        String userList = UserManager.getInstance().listUsers();
        System.out.println(userList);
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "List Users";
    }
}
