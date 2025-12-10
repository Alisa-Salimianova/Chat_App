package io.github.alisa_salimianova.chat.server;

public class MessageFormatter {
    public static String format(String user, String message) {
        return user + ": " + message;
    }
}

