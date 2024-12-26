package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.ui.menus.MenuUsuario;

public class OpcionVolverMenuUsuario implements OpcionMenu {
    private Usuario usuario;
    public OpcionVolverMenuUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public void ejecutar() {
        new MenuUsuario(usuario).start();
    }
}
