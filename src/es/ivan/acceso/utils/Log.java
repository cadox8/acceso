package es.ivan.acceso.utils;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Log {

    @RequiredArgsConstructor
    public enum LogType {
        NORMAL(""),
        WARNING("[AVISO] "),
        ERROR("[ERROR] "),
        DEBUG("[DEBUG] "),
        SUCCESS("[CORRECTO] ");

        @Getter private final String prefix;
    }

    private static final ColoredPrinter debug = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.MAGENTA).build();
    private static final ColoredPrinter error = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.RED).build();
    private static final ColoredPrinter warning = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.YELLOW).build();
    private static final ColoredPrinter normal = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.NONE).build();
    private static final ColoredPrinter success = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.GREEN).build();
    private static final ColoredPrinter div = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.CYAN).build();

    /**
     * Logs como debug
     *
     * @param info el mensaje
     */
    public static void debug(String info){
        log(debug, LogType.DEBUG, info);
    }

    /**
     * Logs como error
     *
     * @param info el mensaje
     */
    public static void error(String info) {
        log(error, LogType.ERROR, info);
    }

    /**
     * Logs como aviso
     *
     * @param info el mensaje
     */
    public static void warning(String info) {
        log(warning, LogType.WARNING, info);
    }

    /**
     * Logs como correcto
     *
     * @param info el mensaje
     */
    public static void success(String info) {
        log(success, LogType.SUCCESS, info);
    }

    /**
     * Logs normal
     *
     * @param info el mensaje
     */
    public static void normal(String info) {
        log(normal, LogType.NORMAL, info);
    }

    /**
     * Muestra el divisor
     */
    public static void div() {
        log(div, LogType.NORMAL, "-------------------------------");
    }

    /**
     * Muestra el divisor
     */
    public static void divWithBreak() {
        log(div, LogType.NORMAL, "\n\n-------------------------------");
    }

    /**
     * Método muy simple para "limpiar" la consola
     */
    public static void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Logs the info
     * @see LogType
     *
     * @param type el tipo
     * @param info el mensaje
     */
    private static void log(ColoredPrinter printer, LogType type, String info){
        printer.setTimestamping(false);
        printer.println(type.getPrefix() + info);
    }
}
