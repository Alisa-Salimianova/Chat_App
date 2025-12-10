package io.github.alisa_salimianova.chat.server;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger {

    private static final String LOG_FILE = "server.log";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static synchronized void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write("[" + LocalDateTime.now().format(FORMATTER) + "] " + message + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка записи в лог: " + e.getMessage());
        }
    }
}
