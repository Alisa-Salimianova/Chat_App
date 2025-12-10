package io.github.alisa_salimianova.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientLogger {

    private final File logFile;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ClientLogger(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Log file path cannot be null or empty");
        }

        this.logFile = new File(filePath);

        try {
            // создаём родительские каталоги
            File parent = logFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            // создаём файл
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to create log file: " + filePath, e);
        }
    }

    public void log(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            bw.write("[" + timestamp + "] " + message);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to log file: " + logFile, e);
        }
    }

    public File getLogFile() {
        return logFile;
    }
}
