package com.edu.ucam.ui;

import com.edu.ucam.modelos.Usuario;

public class MainApp {

    public static void main(String[] args) {

        //login
        MenuLogin menuLogin = new MenuLogin();
        Usuario usuario = null;
        while(usuario == null) {
            usuario = menuLogin.login();
        }

        //menu
        if("admin".equals(usuario.getRol())) {
            MenuAdmin menuAdmin = new MenuAdmin();
            menuAdmin.mostrarMenu();
        } else if("usuario".equals(usuario.getRol())) {
            MenuUsuario menuUsuario = new MenuUsuario();
            menuUsuario.start(usuario);
        }

        

    }
}
