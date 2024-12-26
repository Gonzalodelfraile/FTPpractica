package com.edu.ucam.logica.opciones.menuusuario;

import com.edu.ucam.logica.OpcionMenu;
import com.edu.ucam.logica.ftp.ConexionFTP;
import com.edu.ucam.modelos.Usuario;

public class OpcionListarDirectorioRaiz implements OpcionMenu {
    private Usuario usuario;
    public OpcionListarDirectorioRaiz(Usuario usuario) {
        this.usuario = usuario;
    }
    @Override
    public void ejecutar() {
        System.out.println("Listar directorio raiz");
        ConexionFTP conexionFTP = new ConexionFTP(usuario.getConfiguracionFTP());
        conexionFTP.start();
    }
}
