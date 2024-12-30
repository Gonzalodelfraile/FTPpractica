package edu.ucam.models;

import edu.ucam.ui.View;
import edu.ucam.ui.ViewFactory;
import edu.ucam.utils.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private final String name;
    private final String password;
    private boolean isAdmin;  // Cambio de role a booleano isAdmin
    private FtpConfig activeConfig;
    private List<FtpConfig> configs;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        this.configs = new ArrayList<>();
    }

    public FtpConfig searchConfig(String name) {
        for (FtpConfig config : configs) {
            if (config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public void addConfig(FtpConfig config) {
        if (searchConfig(config.getName()) == null) {
            Log.getInstance().info("Añadiendo configuración: " + config + " al usuario " + name);
            configs.add(config);
        } else {
            ViewFactory.getView().displayError("La configuración " + config.getName() + " ya existe para el usuario " + name);
        }
    }

    public void removeConfig(String name) {
        FtpConfig config = searchConfig(name);
        if (config != null) {
            ViewFactory.getView().display("Eliminando configuración: " + config + " del usuario " + name);
            configs.remove(config);
        } else {
            ViewFactory.getView().displayError("La configuración " + name + " no existe para el usuario " + this.name);
        }
    }

    public void setActiveConfig(String name) {
        FtpConfig config = searchConfig(name);
        if (config != null) {
            ViewFactory.getView().display("Configuración activa: " + config + " para el usuario " + this.name);
            activeConfig = config;
        } else {
            ViewFactory.getView().displayError("La configuración " + name + " no existe para el usuario " + this.name);
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // Se cambió de getRole a isAdmin para reflejar el cambio a booleano
    public boolean isAdmin() {
        return isAdmin;
    }

    public FtpConfig getActiveConfig() {
        return activeConfig;
    }

    public String listConfigs() {
        if (configs.isEmpty()) {
            return "El usuario " + name + " no tiene configuraciones.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Configuraciones de ").append(name).append(":").append("\n");

        for (int i = 0; i < configs.size(); i++) {
            FtpConfig config = configs.get(i);
            sb.append(i + 1).append(". ").append(config.getName()).append("\n");
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +  // Se imprime isAdmin en lugar de role
                ", activeConfig=" + activeConfig +
                ", configs=" + configs +
                '}';
    }
}
