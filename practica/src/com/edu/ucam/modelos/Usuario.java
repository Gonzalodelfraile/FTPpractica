package com.edu.ucam.modelos;

import com.edu.ucam.persistencia.ConfiguracionFTP;
import com.edu.ucam.persistencia.UsuarioManager;
import com.edu.ucam.utilidades.Input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;
    private String password;
    private String rol;
    private ConfiguracionFTP configuracionFTPSeleccionada;
    private List<ConfiguracionFTP> configuracionesFTP;


    public Usuario(String nickname, String password, String rol) {
        this.nickname = nickname;
        this.password = password;
        this.rol = rol;
        this.configuracionesFTP = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public List<ConfiguracionFTP> getConfiguracionesFTP() {
        return new ArrayList<>(configuracionesFTP); //devuelvo una copia de la lista
    }

    public void addConfiguracionFTP(ConfiguracionFTP configuracionFTP) {

        configuracionesFTP.add(configuracionFTP);
        UsuarioManager.getInstance().guardarUsuarios();
    }

    public boolean seleccionarConfiguracionFTP(String nombreConfiguracion) {
        for (ConfiguracionFTP configuracionFTP : configuracionesFTP) {
            if (configuracionFTP.getNombre().equals(nombreConfiguracion)) {
                configuracionFTPSeleccionada = configuracionFTP;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", rol='" + rol + '\'' +
                ", configuracionFTPSeleccionada=" + configuracionFTPSeleccionada +
                ", configuracionesFTP=" + configuracionesFTP +
                '}';
    }

    public void listarConfiguracionesFTP() {
        if(configuracionesFTP.isEmpty()) {
            System.out.println("No hay configuraciones FTP");
            return;
        }

        for (ConfiguracionFTP configuracionFTP : configuracionesFTP) {
            System.out.println(configuracionFTP);
        }
    }


    public boolean existeConfiguracionFTP(String nombre) {
        for (ConfiguracionFTP configuracionFTP : configuracionesFTP) {
            if (configuracionFTP.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
    public void eliminarConfiguracionFTP(String nombre) {
        for (ConfiguracionFTP configuracionFTP : configuracionesFTP) {
            if (configuracionFTP.getNombre().equals(nombre)) {
                configuracionesFTP.remove(configuracionFTP);
                return;
            }
        }
    }

    public ConfiguracionFTP getConfiguracionFTP() {
        return configuracionFTPSeleccionada;
    }


    public void crearUsuario() {

        UsuarioManager usuarioManager = UsuarioManager.getInstance();

        System.out.println("Introduce el nickname del usuario:");
        nickname = Input.leerCadena();
        System.out.println("Introduce la contraseña del usuario:");
        password = Input.leerCadena();
        System.out.println("Introduce el rol del usuario:");
        rol = Input.leerCadena();

        if(nickname == null || password == null || rol == null){
            System.out.println("Error al introducir los datos");
            return;
        } else if (usuarioManager.getUsuario(nickname) != null) {
            System.out.println("Ya existe un usuario con ese nickname");
            return;
        } else {
            Usuario usuario = new Usuario(nickname, password, rol);
            usuarioManager.addUsuario(usuario);
            System.out.println("Usuario creado correctamente");
        }
    }
}
