package edu.ucam.ui;

import edu.ucam.utils.Log;

import java.util.Scanner;

public class MenuUI {
    public static int showMenu(String menuName,String[] options){
        Log.getInstance().debug("Mostrando " + menuName + "...");
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------");
        System.out.println(menuName);
        System.out.println("--------------------");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1) + ". " + options[i]);
        }
        //Si no se introduce un número, se devuelve -1
        try {
            Log.getInstance().debug("Comprobando opción...");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }

    }
}
