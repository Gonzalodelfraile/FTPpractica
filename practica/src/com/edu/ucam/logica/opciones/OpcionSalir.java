package com.edu.ucam.logica.opciones;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;

public class OpcionSalir implements OpcionMenu {
    private Usuario usuario;
    public OpcionSalir(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Saliendo del sistema");


    }
}
