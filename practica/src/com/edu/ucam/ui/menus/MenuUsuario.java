package com.edu.ucam.ui.menus;

import com.edu.ucam.logica.opciones.OpcionSalir;
import com.edu.ucam.logica.opciones.menuusuario.OpcionCerrarSesion;
import com.edu.ucam.logica.opciones.menuusuario.OpcionListarDirectorioRaiz;
import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.logica.opciones.menuusuario.OpcionMenuConfigFTP;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.utilidades.Input;

import java.util.HashMap;
import java.util.Map;

public class MenuUsuario {

    private Map<Integer, OpcionMenu> opciones = new HashMap<>();

    public MenuUsuario(Usuario usuario) {
        opciones.put(1, new OpcionMenuConfigFTP(usuario));
        opciones.put(2, new OpcionListarDirectorioRaiz(usuario));
        opciones.put(3, new OpcionCerrarSesion(usuario));
        opciones.put(4, new OpcionSalir(usuario));
    }

    public void start(){


        int opcion;
        do {
            System.out.println("Menu de usuario");
            System.out.println("1. Configuracion servidor FTP");
            System.out.println("2. Listar directorio raiz");
            System.out.println("3. Cerrar sesion");
            System.out.println("Salir");
            opcion = Input.leerOpcion(1, 4);
            OpcionMenu opcionMenu = opciones.get(opcion);
            if(opcionMenu != null) {
                opcionMenu.ejecutar();
            } else {
                System.out.println("Opcion no valida");
            }

        } while (opcion != 3 && opcion != 4);
    }
}
