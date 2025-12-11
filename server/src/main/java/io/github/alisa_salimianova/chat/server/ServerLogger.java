package io.github.alisa_salimianova.chat.server;

public class ServerLogger {

    private static final String LOG_FILE = "server.log";

    public static synchronized void log(String message) {
        try {
            LoggerUtil.log(LOG_FILE, message);
        } catch (Exception e) {
            System.out.println("Ошибка записи в лог: " + e.getMessage());
        }
    }
}
