package com.edu.ucam.logica.opciones.menuadmin;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.persistencia.UsuarioManager;

public class OpcionListarUsuarios implements OpcionMenu {
    private Usuario usuario;
    public OpcionListarUsuarios(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {

        UsuarioManager usuarioManager = UsuarioManager.getInstance();

        System.out.println("Listando usuarios...");
        usuarioManager.mostrarUsuarios();


    }
}
