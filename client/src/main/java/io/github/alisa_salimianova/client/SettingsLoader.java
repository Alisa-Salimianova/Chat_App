package io.github.alisa_salimianova.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsLoader {

    public static int loadPort(String filename) throws IOException {
        for (String line : Files.readAllLines(Paths.get(filename))) {
            line = line.trim();
            if (line.startsWith("PORT=")) {
                return Integer.parseInt(line.substring(5));
            }
        }
        throw new IOException("PORT not found");
    }
}
