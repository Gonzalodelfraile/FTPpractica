package com.edu.ucam.modelos;

import com.edu.ucam.persistencia.ConfiguracionFTP;

import java.util.ArrayList;
import java.util.List;

public class Usuario {


    private String nickname;
    private String password;
    private String rol;
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
        return configuracionesFTP;
    }

    public void agregarConfiguracionFTP(ConfiguracionFTP configuracionFTP) {
        configuracionesFTP.add(configuracionFTP);
    }

    public void eliminarConfiguracionFTP(ConfiguracionFTP configuracionFTP) {
        configuracionesFTP.remove(configuracionFTP);
    }

    public ConfiguracionFTP getConfiguracionFTP(String nombre) {
        if(configuracionesFTP == null) {
            return null;
        }

        for (ConfiguracionFTP configuracionFTP : configuracionesFTP) {
            if (configuracionFTP.getNombre().equals(nombre)) {
                return configuracionFTP;
            }
        }
        return null;
    }


    public boolean existeConfiguracionFTP(String nombre) {
        return getConfiguracionFTP(nombre) != null;
    }

    public boolean seleccionarConfiguracionFTP(String nombre) {
        ConfiguracionFTP configuracionFTP = getConfiguracionFTP(nombre);
        if (configuracionFTP != null) {
            //seleccionar configuracion
            return true;
        }
        return false;
    }
}
