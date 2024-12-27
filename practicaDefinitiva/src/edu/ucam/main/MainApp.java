package edu.ucam.main;

import edu.ucam.logic.LoginManager;
import edu.ucam.utils.Log;

public class MainApp {
    public static void main(String[] args) {
        Log.getInstance().debug("Iniciando aplicación...");
        // Iniciar sesión
        LoginManager.start();
    }






}
