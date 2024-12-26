package com.edu.ucam.utilidades;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static int leerOpcion(int min, int max) {
        System.out.println("Introduce una opcion:");
        return Validador.esOpcion(scanner.nextLine(), min, max);
    }

    public static String leerCadena() {
        String cadena = scanner.nextLine();
        if (Validador.NoEsVacio(cadena)) {
            return cadena;
        }
        return null;
    }

    public static String leerNickname() {
        System.out.println("Introduce un nickname:");
        String nickname = scanner.nextLine();
        return Validador.esNickname(nickname) ? nickname : null;
    }

    public static String leerPassword() {
        System.out.println("Introduce un password:");
        String password = scanner.nextLine();
        return Validador.esPassword(password) ? password : null;
    }

    public static String leerIP() {
    System.out.println("Introduce una IP:");
    String ip = scanner.nextLine();
    return Validador.esIP(ip) ? ip : null;
}

    public static int leerPuerto() {
        System.out.println("Introduce un puerto:");
        int puerto = Validador.esEntero(scanner.nextLine());
        if(Validador.esPuerto(puerto)) {
            return puerto;
        }
        return -1;
    }

    public static String leerCadena(String s) {
        System.out.println(s);
        return leerCadena();
    }
}
