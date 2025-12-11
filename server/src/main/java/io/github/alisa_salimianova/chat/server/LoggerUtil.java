package io.github.alisa_salimianova.chat.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerUtil {

    private static final DateTimeFormatter TF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerUtil() { }

    public static void log(String path, String message) throws IOException {
        File file = new File(path);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        String ts = LocalDateTime.now().format(TF);
        String line = "[" + ts + "] " + message + System.lineSeparator();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
        }
    }
}
