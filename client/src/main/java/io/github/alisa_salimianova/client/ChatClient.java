package io.github.alisa_salimianova.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ChatClient {

    private static final String LOG_FILE = "client.log";
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        try {
            port = SettingsLoader.getPort();
        } catch (Exception e) {
            System.err.println("Не удалось прочитать настройки, использую localhost:12345");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) username = "anonymous";

            try (Socket socket = new Socket(host, port)) {
                System.out.println("Подключено к серверу " + host + ":" + port);

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                Thread reader = new Thread(new MessageReader(socket));
                reader.setDaemon(true);
                reader.start();

                while (true) {
                    String message = scanner.nextLine();
                    if (message == null) break;

                    if (message.equalsIgnoreCase("/exit")) {
                        String leaveMsg = username + " покинул(а) чат.";
                        out.write(leaveMsg);
                        out.write("\n");
                        out.flush();
                        logToFile("Клиент вышел из чата: " + username);
                        break;
                    }

                    String fullMsg = username + ": " + message;
                    out.write(fullMsg);
                    out.write("\n");
                    out.flush();

                    logToFile(fullMsg);
                }

            } catch (IOException e) {
                System.err.println("Ошибка подключения/IO: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Клиент завершил работу.");
    }

    private static void logToFile(String text) {
        String time = LocalDateTime.now().format(TF);
        String logText = "[" + time + "] " + text + System.lineSeparator();
        try {
            Path p = Path.of(LOG_FILE);
            Files.writeString(p, logText, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Не удалось записать в лог: " + e.getMessage());
        }
    }
}
