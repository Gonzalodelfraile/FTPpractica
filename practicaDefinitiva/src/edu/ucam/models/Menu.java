package edu.ucam.models;

import edu.ucam.ui.View;
import edu.ucam.ui.ViewFactory;
import edu.ucam.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Menú base
public class Menu {
    protected View view;
    protected Map<Integer, Option> options = new HashMap<>();
    protected Scanner scanner = new Scanner(System.in);
    private String title;

    protected static boolean isSessionActive = true;

    public Menu(String title) {
        this.title = title;
        this.view = ViewFactory.getView();
    }

    // mostrar el menú
    public void displayMenu(){
        boolean menuDisplayed = true;
        while (isSessionActive && menuDisplayed) {


            try {
                int optionNumber = view.displayMenu(title, options);
                menuDisplayed = executeOption(optionNumber);
            } catch (NumberFormatException e) {
                view.displayError("Entrada no válida. Por favor, ingrese un número.");
            }
        }
    }

    // Registrar una opción
    public void addOption(int optionNumber, Option option) {
        options.put(optionNumber, option);
    }

    // Ejecutar una opción seleccionada
    public boolean executeOption(int optionNumber) {
        Option option = options.get(optionNumber);
        if (option != null) {
            option.execute();
            return option.isMenuDisplayed();
        } else {
            view.displayError("Opción no válida. Por favor, seleccione una opción válida.");
            return true;
        }
    }

    public static void resetSession() {
        isSessionActive = true;
    }
    public static void closeSession() {
        isSessionActive = false;
    }

    public static boolean isSessionActive() {
        return isSessionActive;
    }

}

