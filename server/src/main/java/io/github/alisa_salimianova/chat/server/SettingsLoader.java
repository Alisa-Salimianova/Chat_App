package io.github.alisa_salimianova.chat.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SettingsLoader {

    private static String filePath;

    public SettingsLoader(String filePath) {
        this.filePath = filePath;
    }

    public static int getPort() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("PORT=")) {
                    return Integer.parseInt(line.substring("PORT=".length()));
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла настроек: " + e.getMessage());
        }

        return 12345;
    }
}
