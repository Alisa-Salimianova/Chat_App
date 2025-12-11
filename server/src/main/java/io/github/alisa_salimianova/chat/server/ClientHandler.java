package io.github.alisa_salimianova.chat.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final ChatServer server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private volatile boolean running = true;
    private String userName = "unknown";

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;

        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize IO streams for client", e);
        }
    }

    @Override
    public void run() {
        try {
            writer.write("Введите ваше имя:\n");
            writer.flush();

            String name = reader.readLine();
            if (name != null && !name.isBlank()) {
                userName = name.trim();
            }
            ServerLogger.log("Пользователь подключился: " + userName);
            server.broadcast("** " + userName + " присоединился к чату **", this);

            String line;
            while (running && (line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase("/exit")) {
                    break;
                }
                String fullMessage = userName + ": " + line;
                ServerLogger.log(fullMessage);
                server.broadcast(fullMessage, this);
            }
        } catch (IOException ignored) {
        } finally {
            close();
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            close();
        }
    }

    public void close() {
        running = false;
        server.removeClient(this);
        ServerLogger.log("Пользователь отключился: " + userName);
        server.broadcast("** " + userName + " вышел из чата **", this);

        try { socket.close(); } catch (IOException ignored) {}
        try { reader.close(); } catch (IOException ignored) {}
        try { writer.close(); } catch (IOException ignored) {}
    }
}
