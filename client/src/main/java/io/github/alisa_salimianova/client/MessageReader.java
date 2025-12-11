package io.github.alisa_salimianova.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;

public class MessageReader implements Runnable {

    private final Socket socket;
    private static final Path LOG = Path.of("client.log");
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MessageReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                logReceived(message);
            }
        } catch (Exception e) {
            System.out.println("Соединение закрыто.");
        }
    }

    private void logReceived(String message) {
        String time = LocalDateTime.now().format(TF);
        String line = "[" + time + "] RECEIVED: " + message + System.lineSeparator();
        try {
            Files.writeString(LOG, line, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception ignored) {}
    }
}
