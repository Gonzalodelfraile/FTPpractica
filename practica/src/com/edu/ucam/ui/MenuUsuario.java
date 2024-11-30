package com.edu.ucam.ui;

import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.persistencia.ConfiguracionFTP;
import com.edu.ucam.utilidades.Input;

public class MenuUsuario {

    public void start(Usuario usuario){


        int opcion;
        do {
            System.out.println("Menu de usuario");
            System.out.println("1. Configuracion servidor FTP");
            System.out.println("2. Listar directorio raiz");
            System.out.println("3. Cerrar sesion");
            System.out.println("Salir");
            opcion = Input.leerOpcion(1, 4);
            if (opcion == 1) {
                menuConfigFTP(usuario);
            } else if (opcion == 2) {
                System.out.println("Listando directorio raiz...");
            } else if (opcion == 3) {
                System.out.println("Cerrando sesion...");
            } else if (opcion == 4) {
                System.out.println("Saliendo...");
            }
        } while (opcion != 3 && opcion != 4);
    }

    public void menuConfigFTP(Usuario usuario) {


        int opcion;
        do {
            System.out.println("Configuracion servidor FTP");
            System.out.println("1. Crear nueva configuracion");
            System.out.println("2. Ver configuraciones");
            System.out.println("3. Seleccionar configuracion");
            System.out.println("4. Volver");
            opcion = Input.leerOpcion(1, 4);
            switch (opcion) {
                case 1 -> crearConfiguracionFTP(usuario);
                case 2 -> listarConfiguraciones(usuario);
                case 3 -> seleccionarConfiguracion(usuario);
                case 4 -> start(usuario);
            }
        } while (opcion != 4);
    }

    private void seleccionarConfiguracion(Usuario usuario) {
        System.out.println("Seleccionar configuracion:");
        String nombre = Input.leerCadena();
        if(nombre == null) {
            System.out.println("Nombre no valido");
            return;
        }
        if(usuario.seleccionarConfiguracionFTP(nombre)) {
            System.out.println("Configuracion seleccionada correctamente");
        } else {
            System.out.println("No se ha podido seleccionar la configuracion");
        }
    }

    private void listarConfiguraciones(Usuario usuario) {
        if(usuario.getConfiguracionesFTP().isEmpty()) {
            System.out.println("No hay configuraciones FTP");
            return;
        }
        System.out.println("Configuraciones FTP:");
        for (ConfiguracionFTP configuracionFTP : usuario.getConfiguracionesFTP()) {
            System.out.println(configuracionFTP.getNombre());
        }
    }

    private void crearConfiguracionFTP(Usuario usuario) {
        System.out.println("Introduce el nombre de la configuracion:");
        String nombre = Input.leerCadena();
        if(nombre == null) {
            System.out.println("Nombre no valido");
            return;
        } else if(usuario.existeConfiguracionFTP(nombre)) {
            System.out.println("Ya existe una configuracion con ese nombre");
            return;
        }

        System.out.println("Introduce la IP del servidor FTP:");
        String ip = Input.leerIP();
        if(ip == null) {
            System.out.println("IP no valida");
            return;
        }
        String user = usuario.getNickname();
        ConfiguracionFTP configuracionFTP = new ConfiguracionFTP(nombre, ip, user);
        usuario.agregarConfiguracionFTP(configuracionFTP);
        System.out.println("Configuracion creada correctamente");

    }

}
