package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;

public class ModificarConfiguracionFTP implements OpcionMenu {
    public ModificarConfiguracionFTP(Usuario usuario) {
    }

    @Override
    public void ejecutar() {
        System.out.println("Modificando configuracion FTP");
    }
}
