package edu.ucam.logic.options;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class DeleteUser implements Option {
    @Override
    public void execute() {
        Log.getInstance().debug("Borrando usuario...");
        UserManager.getInstance().removeUser(userDataInput());

    }

    private String userDataInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre del usuario a borrar: ");
        return scanner.nextLine();
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Delete User";
    }
}
