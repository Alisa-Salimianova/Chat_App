package io.github.alisa_salimianova.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReader implements Runnable {

    private Socket socket;

    public MessageReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;

            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }

        } catch (Exception e) {
            System.out.println("Соединение закрыто.");
        }
    }
}
