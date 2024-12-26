package edu.ucam.logic;

import edu.ucam.models.User;
import edu.ucam.models.menus.AdminMenu;
import edu.ucam.ui.LoginUI;
import edu.ucam.utils.Log;

public class LoginManager {

    public static void login() {
        User usuario;
        do {
            usuario = UserManager.getInstance().login(LoginUI.start());
        } while (usuario == null);
        gestionarMenu(usuario);
    }

    private static void gestionarMenu(User usuario) {
        Log.getInstance().debug("Filtrando usuario...");
        if("admin".equals(usuario.getRole())) {
            // Menu de administrador
            Log.getInstance().debug("Iniciando menú de administrador...");
            new AdminMenu(usuario).showMenu();

        } else if("user".equals(usuario.getRole())) {
            // Menu de usuario
            Log.getInstance().info("Iniciando menú de usuario...");
        } else {
            Log.getInstance().error("Rol de usuario no reconocido: " + usuario.getRole());
        }

        Log.getInstance().debug("Cerrando aplicación...");
    }


}
