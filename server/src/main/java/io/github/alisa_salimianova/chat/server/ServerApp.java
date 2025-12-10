package io.github.alisa_salimianova.chat.server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "12345"));
        new ChatServer(port).start();
    }
}