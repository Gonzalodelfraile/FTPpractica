package edu.ucam.logic;

import edu.ucam.logic.menus.UserMenu;
import edu.ucam.models.User;
import edu.ucam.models.Menu;
import edu.ucam.logic.menus.AdminMenu;
import edu.ucam.ui.LoginUI;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class LoginManager {

    public static void start() {
        boolean logAgain = true;
        while (logAgain) {
            Menu.resetSession();
            login();
            logAgain= logAgain();
        }
    }

    private static boolean logAgain() {
        System.out.println("¿Desea volver a iniciar sesión? (si/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("si");

    }

    public static void login() {
        User usuario;
        do {
            usuario = UserManager.getInstance().login(LoginUI.start());
        } while (usuario == null);
        gestionarMenu(usuario);
        Log.getInstance().debug("Saliendo de la aplicación...");
    }

    private static void gestionarMenu(User user) {
        Log.getInstance().debug("Filtrando usuario...");

        // Usamos el valor isAdmin() para determinar el rol
        if (user.isAdmin()) {
            Log.getInstance().debug("Iniciando menú de administrador...");
            new AdminMenu(user).displayMenu();
        } else {
            Log.getInstance().debug("Iniciando menú de usuario...");
            new UserMenu(user).displayMenu();
        }
    }



}
