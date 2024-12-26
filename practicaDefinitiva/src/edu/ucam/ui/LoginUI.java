package edu.ucam.ui;


import edu.ucam.models.User;


import java.util.Scanner;

public class LoginUI {

    public static User start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce un nickname:");
        String nickname = scanner.nextLine();
        System.out.println("Introduce un password:");
        String password = scanner.nextLine();
        return new User(nickname, password);
    }
}
