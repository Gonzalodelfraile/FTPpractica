package edu.ucam.logic;

import edu.ucam.logic.menus.UserMenu;
import edu.ucam.models.User;
import edu.ucam.models.Menu;
import edu.ucam.logic.menus.AdminMenu;
import edu.ucam.ui.LoginUI;
import edu.ucam.utils.Log;

public class LoginManager {

    public static void start() {
        while (true) { // Reiniciar login mientras la aplicación esté activa
            Menu.resetSession(); // Reiniciar el estado global de sesión
            login(); // Lógica de inicio de sesión
        }

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

        // Usamos el valor booleano isAdmin() para determinar el rol
        if (user.isAdmin()) {
            Log.getInstance().debug("Iniciando menú de administrador...");
            new AdminMenu(user).displayMenu();
        } else {
            Log.getInstance().debug("Iniciando menú de usuario...");
            new UserMenu().displayMenu();
        }
    }



}
