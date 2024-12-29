package edu.ucam.logic.options.admin;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.ui.ViewFactory;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class NewUser implements Option {

    @Override
    public void execute() {
        Log.getInstance().debug("Creando nuevo usuario...");
        UserManager.getInstance().addUser(userDataInput());
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    public User userDataInput() {
        return view.getUserData();
    }

    @Override
    public String toString() {
        return "Nuevo usuario";
    }
}
