package com.edu.ucam.ui.menus;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.logica.opciones.OpcionSalir;
import com.edu.ucam.logica.opciones.menuadmin.OpcionCrearUsuario;
import com.edu.ucam.logica.opciones.menuadmin.OpcionListarUsuarios;
import com.edu.ucam.logica.opciones.menuconfigftp.OpcionVolverMenuUsuario;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.utilidades.Input;

import java.util.HashMap;
import java.util.Map;

public class MenuAdmin {

    private Map<Integer, OpcionMenu> opciones = new HashMap<>();

    public MenuAdmin(Usuario usuario) {
        opciones.put(1, new OpcionCrearUsuario(usuario));
        opciones.put(2, new OpcionVolverMenuUsuario(usuario));
        //opciones.put(3, new OpcionEliminarUsuario(usuario));
        opciones.put(4, new OpcionListarUsuarios(usuario));
        opciones.put(5, new OpcionSalir(usuario));

    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("Menu de administrador");
            System.out.println("1. Crear usuario");
            System.out.println("2. Menu de usuario");
            //System.out.println("3. Eliminar usuario");
            System.out.println("4. Listar usuarios");
            System.out.println("5. Salir");
            opcion = Input.leerOpcion(1, 5);
            OpcionMenu opcionMenu = opciones.get(opcion);
            if(opcionMenu != null) {
                opcionMenu.ejecutar();
            } else {
                System.out.println("Opcion no valida");
            }

        } while (opcion != 5);
    }
}
