package edu.ucam.ui;


import edu.ucam.models.User;
import edu.ucam.utils.Log;


import java.io.Console;
import java.util.Scanner;

public class LoginUI {

    public static User start() {
        Log.getInstance().debug("Iniciando interfaz de login...");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce un nickname:");
        String nickname = scanner.nextLine();


        System.out.println("Introduce un password:");
        //String password = new String(System.console().readPassword()); este no va
        Console console = System.console();
        //leemos el password de forma segura (si se puede)
        String password;
        if(console == null){
            Log.getInstance().error("No se puede leer el password de forma segura");
            password = scanner.nextLine();
        } else {
            password = new String(console.readPassword());
        }
        //String password
        return new User(nickname, password);
    }
}
