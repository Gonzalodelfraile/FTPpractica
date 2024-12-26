package com.edu.ucam.persistencia;

public class ConfiguracionFTP implements java.io.Serializable {

    private String nombre;
    private String servidor;
    private int puertoTransferencia;
    private int puertoConexion;
    private String nickname;
    private String password;


    public ConfiguracionFTP(String Nombre, String servidor, String nickname, String password) {
        this.nombre = Nombre;
        this.servidor = servidor;
        this.puertoConexion = 21;
        this.puertoTransferencia = 20;
        this.nickname = nickname;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
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
                "Nombre='" + nombre + '\'' +
                ", servidor='" + servidor + '\'' +
                ", puertoTransferencia=" + puertoTransferencia +
                ", puertoConexion=" + puertoConexion +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';

    }


    public int getPuertoConexion() {
        return puertoConexion;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
