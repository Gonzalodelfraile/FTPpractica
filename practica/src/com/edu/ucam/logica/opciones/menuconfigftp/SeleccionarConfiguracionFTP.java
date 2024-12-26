package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.utilidades.Input;

public class SeleccionarConfiguracionFTP implements OpcionMenu {
    private Usuario usuario;

    public SeleccionarConfiguracionFTP(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Introduce el nombre de la configuracion FTP que quieres seleccionar:");
        String nombre = Input.leerCadena();
        if (usuario.existeConfiguracionFTP(nombre)) {
            usuario.seleccionarConfiguracionFTP(nombre);
            System.out.println("Configuracion FTP seleccionada correctamente");
        } else {
            System.out.println("No existe ninguna configuracion FTP con ese nombre");
        }


    }
}
