package io.github.alisa_salimianova.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;

public class ChatClient {

    private static final String LOG_FILE = "client.log";

    public static void main(String[] args) {
        try {
            Properties settings = new Properties();
            settings.load(new FileInputStream("src/main/resources/settings.txt"));

            String host = settings.getProperty("HOST");
            int port = Integer.parseInt(settings.getProperty("PORT"));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();

            // Подключение к серверу
            Socket socket = new Socket(host, port);
            System.out.println("Подключено к серверу!");

            // Поток вывода на сервер
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Старт отдельного потока для чтения
            Thread reader = new Thread(new MessageReader(socket));
            reader.start();

            // Основной цикл отправки сообщений
            while (true) {
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("/exit")) {
                    out.println(username + " покинул чат.");
                    log("Клиент вышел из чата");
                    break;
                }

                String fullMsg = username + ": " + message;
                out.println(fullMsg);
                log(fullMsg);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void log(String text) throws IOException {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logText = "[" + time + "] " + text + System.lineSeparator();

        Files.write(
                new File(LOG_FILE).toPath(),
                logText.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
    }
}
