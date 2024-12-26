package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.utilidades.Input;

public class EliminarConfiguracionFTP implements OpcionMenu {
    private Usuario usuario;
    public EliminarConfiguracionFTP(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {
        System.out.println("Introduce el nombre de la configuración FTP a eliminar:");
        String nombre = Input.leerCadena();
        if(usuario.existeConfiguracionFTP(nombre)) {
            usuario.eliminarConfiguracionFTP(nombre);
            System.out.println("Configuración FTP eliminada correctamente");
        } else {
            System.out.println("No existe ninguna configuración FTP con ese nombre");
        }
    }
}
