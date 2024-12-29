package edu.ucam.ui;

import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.util.Map;
import java.util.Scanner;

public class ConsoleView implements View {

    private static ConsoleView instance;
    private Scanner scanner;

    private ConsoleView() {
        scanner = new Scanner(System.in);
    }


    public static ConsoleView getInstance() {
        if (instance == null) {
            instance = new ConsoleView();
        }
        return instance;
    }

    @Override
    public void display(String message) {
        //System.out.println(message);
        Log.getInstance().info(message);
    }

    @Override
    public void displayError(String message) {
        //System.err.println(message);
        Log.getInstance().error(message);
    }

    @Override
    public int displayMenu(String title, Map<Integer, Option> options) throws NumberFormatException {
        System.out.println("----------" + title + "----------");
        options.forEach((key, value) -> System.out.println(key + ". " + value.toString()));
        System.out.println("Introduce una opción:");

        return Integer.parseInt(scanner.nextLine());

    }

    @Override
    public User getUserData() {
        System.out.println("Nombre: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Contraseña: ");
        String password = scanner.nextLine();
        System.out.println("¿Es administrador? (si/no): ");
        String role = scanner.nextLine();
        boolean isAdmin = role.equalsIgnoreCase("si");
        return new User(name, password, isAdmin);
    }

    @Override
    public String getInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public void update() {

    }

}
