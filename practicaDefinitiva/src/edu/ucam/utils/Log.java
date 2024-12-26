package edu.ucam.utils;


import java.io.IOException;
import java.util.logging.*;

public class Log {
    private static Log instancia; // Singleton
    private final Logger logger;

    private Log() {
        logger = Logger.getLogger("FTPLogger");
        logger.setUseParentHandlers(false); // No hereda los handlers de la clase padre

        try{
            FileHandler fileHandler = new FileHandler("ftp.log",true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL); // Loguea todo a archivo
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new FormatoLogConsola());
            consoleHandler.setLevel(Level.INFO); // Loguea solo los aciertos y errores a consola
            logger.addHandler(consoleHandler);

            logger.setLevel(Level.ALL); // Loguea todo
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de log");
        }
    }

    public static synchronized Log getInstance() {
        if (instancia == null) {
            instancia = new Log();
        }
        return instancia;
    }

    public void info(String mensaje) {
        logger.log(Level.INFO, mensaje);
    }

    public void warning(String mensaje) {
        logger.log(Level.WARNING, mensaje);
    }

    public void error(String mensaje) {
        logger.log(Level.SEVERE, mensaje);
    }

    public void error(String mensaje, Exception e) {
        logger.log(Level.SEVERE, mensaje, e);
    }

    public void debug(String mensaje) {
        logger.log(Level.FINE, mensaje);
    }
}
