package com.edu.ucam.logica.opciones.menuconfigftp;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.logica.ftp.ProbarConexionFTP;
import com.edu.ucam.modelos.Usuario;
import com.edu.ucam.persistencia.ConfiguracionFTP;
import com.edu.ucam.utilidades.Input;

public class CrearConfiguracionFTP implements OpcionMenu {

    private Usuario usuario;

    public CrearConfiguracionFTP(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar() {

        String nombre = Input.leerCadena("Introduce el nombre de la configuracion FTP: ");



        String ip = Input.leerIP();
        String nickname = Input.leerCadena("Introduce el nickname: ");
        String password = Input.leerPassword();

        if(usuario.existeConfiguracionFTP(nombre)){
            System.out.println("Ya existe una configuracion FTP con ese nombre");
            return;
        }
        if(nombre == null || ip == null || nickname == null || password == null){
            System.out.println("Error al introducir los datos");
            return;
        }

        ConfiguracionFTP configuracionFTP = new ConfiguracionFTP(nombre, ip, nickname, password);
        //comprobar si se puede conectar al servidor FTP
        ProbarConexionFTP probarConexionFTP = new ProbarConexionFTP(configuracionFTP);

        String mensaje = probarConexionFTP.probarConexion();

        if(mensaje.startsWith("ERROR")){

            System.err.println(mensaje);
            return;
        }
        System.out.println("Configuracion FTP creada correctamente");

        usuario.addConfiguracionFTP(configuracionFTP);
    }
}
