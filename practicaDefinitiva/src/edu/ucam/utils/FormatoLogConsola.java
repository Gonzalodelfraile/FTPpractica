package edu.ucam.utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoLogConsola extends Formatter {
    // Colores ANSI para consola
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();

        // Agregar el nivel del log con el color adecuado
        String levelName = record.getLevel().getName();
        String color = RESET;

        // Establecer el color según el nivel del log
        switch (levelName) {
            case "INFO":
                color = GREEN;
                levelName = "INFO";
                break;
            case "WARNING":
                color = YELLOW;
                levelName = "WARNING";
                break;
            case "SEVERE":
                color = RED;
                levelName = "ERROR";
                break;
        }

        // Formato para la salida en consola
        sb.append(color).append("[").append(levelName).append("] ");
        sb.append(formatMessage(record)).append(RESET);
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
