package com.edu.ucam.ui;

import com.edu.ucam.logica.ftp.ConexionFTP;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.ui.menus.MenuAdmin;
import com.edu.ucam.ui.menus.Login;
import com.edu.ucam.ui.menus.MenuUsuario;

public class MainApp {

    public static void main(String[] args) {
        //login
        Usuario usuario = null;
        while(usuario == null) {
            usuario = Login.iniciar();
        }

        //menu
        if("admin".equals(usuario.getRol())) {
            MenuAdmin menuAdmin = new MenuAdmin(usuario);
            menuAdmin.mostrarMenu();
        } else if("usuario".equals(usuario.getRol())) {
            MenuUsuario menuUsuario = new MenuUsuario(usuario);
            menuUsuario.start();
        }
    }

}
