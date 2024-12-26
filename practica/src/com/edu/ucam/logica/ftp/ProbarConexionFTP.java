package com.edu.ucam.logica.ftp;

import com.edu.ucam.persistencia.ConfiguracionFTP;

import java.io.*;
import java.net.Socket;

public class ProbarConexionFTP {

    private String servidor;
    private String usuario;
    private String password;
    private int puertoConexion;

    public ProbarConexionFTP(ConfiguracionFTP configuracionFTP) {
        this.servidor = configuracionFTP.getServidor();
        this.usuario = configuracionFTP.getNickname();
        this.password = configuracionFTP.getPassword();
        this.puertoConexion = configuracionFTP.getPuertoConexion();
    }

    public String probarConexion() {

        try (Socket socket = new Socket(servidor, puertoConexion);
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar comando USER
            pw.println("USER " + usuario);

            String mensajeBienvenida;
            // Leer la bienvenida del servidor
            while ((mensajeBienvenida = br.readLine()).startsWith("220-")) {
                //System.out.println(mensajeBienvenida);
            }

            //leer contraseña
            br.readLine();
            pw.println("PASS " + password);
            String respuestaLogin = br.readLine();
            //System.out.println(respuestaLogin);

            // Verificar si el login fue exitoso
            if (!respuestaLogin.startsWith("230")) {
                //System.out.println(respuestaLogin);
                return "ERROR: No se pudo autenticar";
            }

            // Cambiar a modo pasivo
            pw.println("PASV");
            String respuesta = br.readLine();
            //System.out.println(respuesta);

            // Extraer IP y puerto del servidor de datos
            String[] parts = respuesta.split(",");
            String ip = parts[0].substring(parts[0].lastIndexOf('(') + 1) + "." + parts[1] + "." + parts[2] + "." + parts[3];
            int port = Integer.parseInt(parts[4]) * 256 + Integer.parseInt(parts[5].substring(0, parts[5].indexOf(')')));
            //System.out.println("IP: " + ip + ", Puerto: " + port);
            // Conectar al servidor de datos
            try (Socket dataSocket = new Socket(ip, port);
                 BufferedReader pwDatos = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()))) {

                // Enviar comando LIST
                pw.println("LIST");
                String datos;
                while ((datos = pwDatos.readLine()) != null) {

                    //System.out.println(datos);
                }
            }

            // Enviar comando QUIT para cerrar la sesión
            pw.println("QUIT");
            //System.out.println(br.readLine());

        } catch (IOException e) {
            return "ERROR: No se pudo conectar al servidor FTP";
        }
        return "SUCCESS";
    }


}
