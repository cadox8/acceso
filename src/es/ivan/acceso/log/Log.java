package es.ivan.acceso.log;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class Log {

    private static final ColoredPrinter debug = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.MAGENTA).build();
    private static final ColoredPrinter error = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.RED).build();
    private static final ColoredPrinter warning = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.YELLOW).build();
    private static final ColoredPrinter normal = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.NONE).build();
    private static final ColoredPrinter success = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.GREEN).build();
    private static final ColoredPrinter div = new ColoredPrinter.Builder(1, true).foreground(Ansi.FColor.CYAN).build();
    // Log file
    private static final File logFile = new File("./logs/log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_uuuu_HH_mm_ss", Locale.ENGLISH)) + ".log");

    /**
     * Logs como debug
     *
     * @param info el mensaje
     */
    public static void debug(String info) {
        log(debug, LogType.DEBUG, info);
        Log.toFile(LogType.DEBUG.getPrefix() + info);
    }

    /**
     * Logs como error
     *
     * @param info el mensaje
     */
    public static void error(String info) {
        log(error, LogType.ERROR, info);
        Log.toFile(LogType.ERROR.getPrefix() + info);
    }

    /**
     * Logs como stack al archivo
     * NO lo muestra por consola
     *
     * @param stack el mensaje
     */
    public static void stack(StackTraceElement[] stack) {
        Log.toFile(LogType.STACK.getPrefix() + Arrays.stream(stack).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        log(warning, LogType.WARNING, "Error guardado en archivo .log");
    }

    /**
     * Logs como aviso
     *
     * @param info el mensaje
     */
    public static void warning(String info) {
        log(warning, LogType.WARNING, info);
        Log.toFile(LogType.WARNING.getPrefix() + info);
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
     * Logs normal
     *
     * @param info el mensaje
     */
    public static void info(String info) {
        log(div, LogType.NORMAL, info);
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
    public static void putBreak(int breaks) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < breaks; i++) sb.append("\n");
        log(div, LogType.NORMAL, sb.toString());
    }

    /**
     * Muestra el divisor
     */
    public static void divWithBreak() {
        log(div, LogType.NORMAL, "\n\n-------------------------------");
    }

    public static void divWithBreakEnd() {
        log(div, LogType.NORMAL, "-------------------------------\n\n");
    }

    /**
     * Método muy simple para limpiar la consola
     * <p>
     * *AVISO*: Este método sólo funciona cuando la consola NO es la de IntelliJ, Eclipse o NetBeans, puesto que sus consolas no son reales.
     * Esto borra las consolas de Windows y (debería) Linux
     */
    public static void clear() {
        try {
            final String os = System.getProperty("os.name");
            if (os.toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("cmd", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }

    /**
     * Logs the info
     *
     * @param type el tipo
     * @param info el mensaje
     * @see LogType
     */
    private static void log(ColoredPrinter printer, LogType type, String info) {
        printer.setTimestamping(false);
        printer.println(type.getPrefix() + info);
    }

    private static void toFile(String info) {
        final String time = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)) + "]";

        try {
            Files.write(logFile.toPath(), Collections.singletonList(time + info), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createLogFile() {
        if (!logFile.getParentFile().exists()) logFile.getParentFile().mkdirs();
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiredArgsConstructor
    public enum LogType {
        NORMAL(""),
        WARNING("[!] "),
        ERROR("[X] "),
        DEBUG("[✎] "),
        SUCCESS("[✔] "),
        STACK("[☄] ");

        @Getter
        private final String prefix;
    }
}
