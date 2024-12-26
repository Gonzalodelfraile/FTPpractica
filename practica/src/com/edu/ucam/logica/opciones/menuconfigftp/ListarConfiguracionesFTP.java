package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;

public class ListarConfiguracionesFTP implements OpcionMenu {
    private Usuario usuario;
    public ListarConfiguracionesFTP(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Listando configuraciones FTP...");
        usuario.listarConfiguracionesFTP();

    }

}
