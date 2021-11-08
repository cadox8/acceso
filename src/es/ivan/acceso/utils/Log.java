package es.ivan.acceso.utils;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Log {

    @RequiredArgsConstructor
    public enum LogType {
        NORMAL(""),
        DIV(""),
        WARNING("[AVISO]"),
        ERROR("[ERROR]"),
        DEBUG("[DEBUG]");

        @Getter private final String prefix;
    }

    private static final ColoredPrinter debug = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.MAGENTA).build();
    private static final ColoredPrinter error = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.RED).build();
    private static final ColoredPrinter warning = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.YELLOW).build();
    private static final ColoredPrinter normal = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.NONE).build();
    private static final ColoredPrinter div = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.CYAN).build();

    /**
     * Logs the info as Debug
     *
     * @param info The object to be logged
     */
    public static void log(String info){
        log(debug, LogType.DEBUG, info);
    }

    /**
     * Logs the info as Danger
     *
     * @param info The object to be logged
     */
    public static void error(String info) {
        log(error, LogType.ERROR, info);
    }

    /**
     * Logs the info as Warning
     *
     * @param info The object to be logged
     */
    public static void warning(String info) {
        log(warning, LogType.WARNING, info);
    }

    /**
     * Logs the info as Normal
     *
     * @param info The object to be logged
     */
    public static void normal(String info) {
        log(normal, LogType.NORMAL, info);
    }

    /**
     * Muestra el divisor
     */
    public static void div() {
        log(div, LogType.DIV, "-------------------------------");
    }

    /**
     * Muestra el divisor
     */
    public static void divWithBreak() {
        log(div, LogType.DIV, "\n\n-------------------------------");
    }

    /**
     * Método muy simple para "limpiar" la consola
     */
    public static void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Logs the info as the type you select
     * @see LogType
     *
     * @param type The log type
     * @param text The object to be logged
     */
    private static void log(ColoredPrinter printer, LogType type, String text){
        printer.setTimestamping(false);
        printer.println(type == LogType.DIV ? text : type.getPrefix() + " " + text);
    }
}
