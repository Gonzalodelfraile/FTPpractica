package edu.ucam.models;

import edu.ucam.utils.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String password;
    private String role;
    private FTPConfig activeConfig;
    private List<FTPConfig> configs;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.configs = new ArrayList<>();
    }

    public FTPConfig searchConfig(String name) {
        for (FTPConfig config : configs) {
            if (config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public void addConfig(FTPConfig config) {
        if(searchConfig(config.getName()) == null) {
            Log.getInstance().info("Añadiendo configuración: " + config + " al usuario " + name);
            configs.add(config);
        } else {
            Log.getInstance().error("La configuración " + config.getName() + " ya existe para el usuario " + name);
        }
    }

    public void removeConfig(String name) {
        FTPConfig config = searchConfig(name);
        if(config != null) {
            Log.getInstance().info("Eliminando configuración: " + config + " del usuario " + name);
            configs.remove(config);
        } else {
            Log.getInstance().error("La configuración " + name + " no existe para el usuario " + name);
        }
    }

    public void setActiveConfig(String name) {
        FTPConfig config = searchConfig(name);
        if(config != null) {
            Log.getInstance().info("Configuración activa para el usuario " + this.name + ": " + config);
            activeConfig = config;
        } else {
            Log.getInstance().error("La configuración " + name + " no existe para el usuario " + this.name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public FTPConfig getActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(FTPConfig activeConfig) {
        this.activeConfig = activeConfig;
    }

    public List<FTPConfig> getConfigs() {
        return new ArrayList<>(configs); //devuelvo una copia de la lista
    }

    public void setConfigs(List<FTPConfig> configs) {
        this.configs = configs;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", activeConfig=" + activeConfig +
                ", configs=" + configs +
                '}';
    }
}
