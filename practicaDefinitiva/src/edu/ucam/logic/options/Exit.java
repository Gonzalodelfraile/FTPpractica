package edu.ucam.logic.options;

import edu.ucam.logic.UserManager;
import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class Exit implements Option {
    @Override
    public void execute() {
        UserManager.getInstance().saveUsers();
        Log.getInstance().debug("Saliendo de la aplicación...");
        System.exit(0);
    }

    @Override
    public boolean isMenuDisplayed() {
        return false;
    }

    @Override
    public String toString() {
        return "Salir";
    }
}
