package com.edu.ucam.logica.opciones.menuadmin;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;

public class OpcionCrearUsuario implements OpcionMenu {
    private Usuario usuario;

    public OpcionCrearUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Creando usuario...");
        usuario.crearUsuario();
    }
}
