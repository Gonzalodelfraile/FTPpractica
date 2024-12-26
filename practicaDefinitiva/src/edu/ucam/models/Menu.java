package edu.ucam.models;

import edu.ucam.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Menú base
public class Menu {
    protected Map<Integer, Option> options = new HashMap<>();
    protected Scanner scanner = new Scanner(System.in);
    private String title;

    public Menu(String title) {
        this.title = title;
    }

    // mostrar el menú
    public void displayMenu(){
        boolean menuDisplayed = true;
        while (menuDisplayed) {

            System.out.println("\n---" + title + "---");
            options.forEach((key, option) -> System.out.println(key + ". " + option));
            System.out.println("Seleccione una opción:");
            int optionNumber = Integer.parseInt(scanner.nextLine());
            menuDisplayed = executeOption(optionNumber);
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
            Log.getInstance().error("Opción no válida.");
            return true;
        }
    }
}

