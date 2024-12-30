package edu.ucam.logic.persistance;

import edu.ucam.ui.ViewFactory;
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
            ViewFactory.getView().display("Objeto guardado correctamente en: " + path);
        } catch (IOException e) {
            ViewFactory.getView().displayError("Error al guardar el objeto en: " + path);
        }
    }

    /**
     * Carga un objeto desde un archivo
     * @param path Ruta del archivo
     * @return Objeto cargado
     */
    public static Object load(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ViewFactory.getView().display("Cargando objeto desde: " + path);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            ViewFactory.getView().displayError("Error al cargar el objeto desde: " + path);
            return null;
        }
    }
}
