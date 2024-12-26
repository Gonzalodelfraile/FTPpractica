package com.edu.ucam.ui.menus;

import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.persistencia.UsuarioManager;
import com.edu.ucam.utilidades.Input;

import java.util.Scanner;

public class Login {

    public static Usuario iniciar() {

            String nickname = Input.leerNickname();
            String password = Input.leerPassword();
            return autenticarUsuario(nickname, password);
    }

    private static Usuario autenticarUsuario(String nickname, String password) {
        UsuarioManager usuarioManager = UsuarioManager.getInstance();
        if (usuarioManager.validarUsuario(nickname, password)) {
            return usuarioManager.getUsuario(nickname);
        }
        System.out.println("Usuario o contraseña incorrectos");
        return null;
    }
}
