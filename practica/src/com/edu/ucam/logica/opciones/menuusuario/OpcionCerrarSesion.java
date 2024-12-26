package com.edu.ucam.logica.opciones.menuusuario;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;

public class OpcionCerrarSesion implements OpcionMenu {
    private Usuario usuario;

    public OpcionCerrarSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Cerrando sesion");
    }
}
