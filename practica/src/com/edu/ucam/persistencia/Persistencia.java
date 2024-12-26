package com.edu.ucam.persistencia;

import java.io.*;

public class Persistencia {

    /**
     * Guarda un objeto serializable en un archivo.
     *
     * @param objeto      El objeto a guardar.
     * @param rutaArchivo La ruta del archivo donde se guardará el objeto.
     * @throws IOException Si ocurre un error durante la escritura.
     */
    public static void guardar( String rutaArchivo,Object objeto){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(objeto);
            System.out.println("Objeto guardado correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el objeto en: " + rutaArchivo);
        }
    }

    /**
     * Carga un objeto serializable desde un archivo.
     *
     * @param rutaArchivo La ruta del archivo desde donde se cargará el objeto.
     * @return El objeto cargado.
     * @throws IOException            Si ocurre un error durante la lectura.
     * @throws ClassNotFoundException Si la clase del objeto no puede ser encontrada.
     */

    public static Object cargar(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            System.out.println("Objeto cargado correctamente desde: " + rutaArchivo);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el objeto desde: " + rutaArchivo);
            return null;
        }
    }
}

