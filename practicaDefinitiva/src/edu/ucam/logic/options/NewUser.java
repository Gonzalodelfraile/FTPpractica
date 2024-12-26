package edu.ucam.logic.options;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.models.User;
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
        System.out.println("Nombre: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Contraseña: ");
        String password = scanner.nextLine();
        System.out.println("Rol: ");
        String role = scanner.nextLine();
        return new User(name, password, role);
    }

    @Override
    public String toString() {
        return "New User";
    }
}
