package com.edu.ucam.logica.opciones.menuusuario;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.ui.menus.MenuConfigFTP;

public class OpcionMenuConfigFTP implements OpcionMenu {
    private Usuario usuario;
    public OpcionMenuConfigFTP(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public void ejecutar() {
        new MenuConfigFTP(usuario).ejecutar();
    }
}
