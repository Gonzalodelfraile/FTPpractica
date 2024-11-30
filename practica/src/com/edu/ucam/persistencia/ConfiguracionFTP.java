package com.edu.ucam.persistencia;

public class ConfiguracionFTP {

    private String Nombre;
    private String servidor;
    private int puertoTransferencia;
    private int puertoConexion;
    private String nickname;

    public ConfiguracionFTP(String Nombre, String servidor, String nickname) {
        this.Nombre = Nombre;
        this.servidor = servidor;
        this.puertoConexion = 21;
        this.puertoTransferencia = 20;
        this.nickname = nickname;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getServidor() {
        return servidor;
    }

    public int getPuertoTransferencia() {
        return puertoTransferencia;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "ConfiguracionFTP{" +
                "Nombre='" + Nombre + '\'' +
                ", servidor='" + servidor + '\'' +
                ", puerto=" + puertoTransferencia +
                ", nickname='" + nickname + '\'' +
                '}';
    }

}
