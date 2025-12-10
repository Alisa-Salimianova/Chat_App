package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.client.SettingsLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SettingsLoaderTest {

    @Test
    void testLoadPortCorrect(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("settings.txt");
        Files.writeString(file, "PORT=12345");

        int port = SettingsLoader.loadPort(file.toString());

        assertEquals(12345, port);
    }

    @Test
    void testLoadPortMissingFile() {
        assertThrows(IOException.class, () -> {
            SettingsLoader.loadPort("nonexistent.txt");
        });
    }

    @Test
    void testLoadPortInvalidFormat(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("settings.txt");
        Files.writeString(file, "PORT=abc"); // неверный формат

        assertThrows(NumberFormatException.class, () -> {
            SettingsLoader.loadPort(file.toString());
        });
    }
}
