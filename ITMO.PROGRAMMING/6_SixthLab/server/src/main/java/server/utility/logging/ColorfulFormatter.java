package server.utility.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ColorfulFormatter extends Formatter {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String WHITE = "\u001B[37m";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String color;
        switch (record.getLevel().getName()) {
            case "SEVERE":
                color = RED;
                break;
            case "WARNING":
                color = YELLOW;
                break;
            case "INFO":
                color = BLUE;
                break;
            case "CONFIG":
                color = GREEN;
                break;
            case "FINE":
                color = CYAN;
                break;
            case "FINER":
                color = MAGENTA;
                break;
            case "FINEST":
                color = WHITE;
                break;
            default:
                color = RESET;
        }

        String time = dateFormat.format(new Date(record.getMillis()));

        return String.format("%s[%s] [%s] [%s]%s: %s%s%n",
                color, time, record.getLevel(), record.getLoggerName(), RESET, record.getMessage(), RESET);
    }
}

