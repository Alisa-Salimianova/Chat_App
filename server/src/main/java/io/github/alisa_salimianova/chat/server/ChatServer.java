package io.github.alisa_salimianova.chat.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private final int port;
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Chat server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            ClientHandler handler = new ClientHandler(socket, this);
            clients.add(handler);

            new Thread(handler).start();
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler c : clients) {
            if (c != sender) {
                c.send(message);
            }
        }
    }

    public void remove(ClientHandler client) {
        clients.remove(client);
    }

}
