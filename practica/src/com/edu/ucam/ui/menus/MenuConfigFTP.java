package com.edu.ucam.ui.menus;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.logica.opciones.menuconfigftp.*;
import com.edu.ucam.modelos.Usuario;

import java.util.HashMap;
import java.util.Map;
import com.edu.ucam.logica.opciones.OpcionSalir;
import com.edu.ucam.utilidades.Input;

public class MenuConfigFTP implements OpcionMenu {
    private Usuario usuario;
    private Map<Integer,OpcionMenu> opciones = new HashMap<>();


    public MenuConfigFTP(Usuario usuario) {
        this.usuario = usuario;
        opciones.put(1, new CrearConfiguracionFTP(usuario));
        opciones.put(2, new SeleccionarConfiguracionFTP(usuario));
        opciones.put(3, new ModificarConfiguracionFTP(usuario));
        opciones.put(4, new EliminarConfiguracionFTP(usuario));
        opciones.put(5, new ListarConfiguracionesFTP(usuario));
        opciones.put(6, new OpcionVolverMenuUsuario(usuario));
        opciones.put(7, new OpcionSalir(usuario));

    }

    @Override
    public void ejecutar() {
        int opcion;
        do {
            System.out.println("Menu de configuracion FTP");
            System.out.println("1. Crear configuracion FTP");
            System.out.println("2. Seleccionar configuracion FTP");
            System.out.println("3. Modificar configuracion FTP");
            System.out.println("4. Eliminar configuracion FTP");
            System.out.println("5. Listar configuraciones FTP");
            System.out.println("6. Volver al menu de usuario");
            System.out.println("7. Salir");
            opcion = Input.leerOpcion(1, 7);
            OpcionMenu opcionMenu = opciones.get(opcion);
            if(opcionMenu != null) {
                opcionMenu.ejecutar();
            } else {
                System.out.println("Opcion no valida");
            }

        } while (opcion != 6 && opcion != 7);
    }
}
