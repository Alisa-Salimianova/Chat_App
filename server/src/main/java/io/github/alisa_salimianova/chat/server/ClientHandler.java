package io.github.alisa_salimianova.chat.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final ChatServer server;
    private PrintWriter out;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to chat!");

            String msg;
            while ((msg = in.readLine()) != null) {
                server.broadcast(msg, this);
            }

        } catch (IOException ignored) {
        } finally {
            server.remove(this);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    public void send(String msg) {
        if (out != null) out.println(msg);
    }
}
