package edu.ucam.models;

import java.io.Serializable;

public class FTPConfig implements Serializable {
    private String name;
    private String serverIP;
    private int port;
    private String user;
    private String password;

    public FTPConfig(String name, String serverIP, int port, String user, String password) {
        this.name = name;
        this.serverIP = serverIP;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "FTPConfig{" +
                "name='" + name + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
