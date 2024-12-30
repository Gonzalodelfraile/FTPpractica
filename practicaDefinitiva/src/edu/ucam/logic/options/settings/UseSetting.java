package edu.ucam.logic.options.settings;

import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class UseSetting implements Option {
    private User user;

    public UseSetting(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Seleccion de configuración...");
        String name = view.getInput("Nombre de la configuración: ");
        user.setActiveConfig(name);

    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Seleccionar configuración";
    }
}
