package io.github.alisa_salimianova.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.*;

public class ChatServer {

    private final int port;

    private volatile ServerSocket serverSocket;
    private volatile boolean running = false;

    private final ExecutorService clientPool = Executors.newCachedThreadPool();
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("Chat server started on port " + port);

            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(socket, this);
                    clients.add(handler);
                    clientPool.submit(handler);
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Accept failed: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not start server on port " + port, e);
        } finally {
            stop();
        }
    }

    public Thread startAsync() {
        Thread t = new Thread(this::start);
        t.start();

        try { Thread.sleep(60); } catch (InterruptedException ignored) {}

        return t;
    }

    public void stop() {
        running = false;

        if (serverSocket != null && !serverSocket.isClosed()) {
            try { serverSocket.close(); } catch (IOException ignored) {}
        }

        for (ClientHandler c : clients) {
            c.close();
        }
        clients.clear();

        clientPool.shutdownNow();
        try {
            clientPool.awaitTermination(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {}
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler c : clients) {
            if (c != sender) {
                c.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        new ChatServer(12345).start();
    }
}
