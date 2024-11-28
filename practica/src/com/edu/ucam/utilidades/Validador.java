package com.edu.ucam.utilidades;

public class Validador {

    public static boolean NoEsVacio(String texto) {
        return texto != null && !texto.isEmpty();
    }

    public static boolean esIP(String ip) {
        return ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    public static boolean esPuerto(String puerto) {
        return puerto.matches("^[0-9]{1,5}$");
    }

    public static boolean esNickname(String nickname) {
        return nickname.matches("^[a-zA-Z0-9]{1,20}$");
    }

    public static boolean esPassword(String password) {
        return password.matches("^[a-zA-Z0-9]{1,20}$");
    }

    public static boolean esNombreArchivo(String nombreArchivo) {
        return nombreArchivo.matches("^[a-zA-Z0-9]{1,20}$");
    }

    public static boolean esComando(String comando, String[] comandos) {
        for (String c : comandos) {
            if (c.equals(comando)) {
                return true;
            }
        }
        return false;
    }

}
