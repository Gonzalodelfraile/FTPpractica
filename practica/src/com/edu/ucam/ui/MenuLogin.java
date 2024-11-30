package com.edu.ucam.ui;

import com.edu.ucam.modelos.Usuario;

import java.util.Scanner;

public class MenuLogin {

    public Usuario login() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce tu nickname de usuario:");
        String nickname = scanner.nextLine();

        System.out.println("Introduce tu password:");
        String password = scanner.nextLine();

        //validar usuario (puede cambiar para leer de archivo o base de datos)
        if (nickname.equals("admin") && password.equals("admin")) {
            return new Usuario(nickname, password, "admin");
        } else if (nickname.equals("usuario") && password.equals("usuario")) {
            return new Usuario(nickname, password, "usuario");
        } else {
            return null;
        }
    }
}
