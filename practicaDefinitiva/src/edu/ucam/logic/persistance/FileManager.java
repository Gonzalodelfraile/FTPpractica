package edu.ucam.logic.persistance;

import edu.ucam.utils.Log;

import java.io.*;

public class FileManager {

    /**
     * Guarda un objeto en un archivo
     * @param path Ruta del archivo
     * @param content Objeto a guardar
     */
    public static void save(String path, Object content) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(content);
            Log.getInstance().info("Objeto guardado correctamente en: " + path);
        } catch (IOException e) {
            Log.getInstance().error("Error al guardar el objeto en: " + path, e);
        }
    }

    /**
     * Carga un objeto desde un archivo
     * @param path Ruta del archivo
     * @return Objeto cargado
     */
    public static Object load(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Log.getInstance().info("Objeto cargado correctamente desde: " + path);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.getInstance().error("Error al cargar el objeto desde: " + path, e);
            return null;
        }
    }
}
