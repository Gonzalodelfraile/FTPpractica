package edu.ucam.utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoLogConsola extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(dateFormat.format(new Date(record.getMillis()))).append("] ");
        sb.append("[").append(String.format("%-7s", record.getLevel().getName())).append("] ");
        sb.append(formatMessage(record));
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}