package io.github.alisa_salimianova.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingsLoader {

    private static final String SETTINGS_FILE = "/settings.txt";

    public static int getPort() {
        try (InputStream is = SettingsLoader.class.getResourceAsStream(SETTINGS_FILE)) {
            if (is == null) {
                System.err.println("Файл настроек не найден, использую порт 12345");
                return 12345;
            }
            return parsePort(new BufferedReader(new InputStreamReader(is)));
        } catch (IOException e) {
            return 12345;
        }
    }

    public static int loadPortFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return parsePort(br);
        } catch (IOException e) {
            return 12345;
        }
    }

    private static int parsePort(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("PORT=")) {
                return Integer.parseInt(line.substring("PORT=".length()));
            }
        }
        return 12345;
    }
}

