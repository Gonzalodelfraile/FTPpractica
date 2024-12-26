package edu.ucam.logic.options;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class LogOut implements Option {

    @Override
    public void execute() {
        Log.getInstance().debug("Cerrando sesión...");
        UserManager.getInstance().saveUsers();

    }

    @Override
    public boolean isMenuDisplayed() {
        return false;
    }

    @Override
    public String toString() {
        return "Cerrar sesión";
    }
}
