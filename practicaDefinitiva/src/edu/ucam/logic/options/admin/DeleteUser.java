package edu.ucam.logic.options.admin;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class DeleteUser implements Option {
    private User user;

    public DeleteUser(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Borrando usuario...");
        UserManager.getInstance().removeUser(userDataInput(), user);

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
        return "Borrar usuario";
    }
}
