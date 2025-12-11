package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.client.SettingsLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsLoaderTest {

    @Test
    void testLoadPortCorrect() throws Exception {
        File f = new File("test_settings.txt");
        try (FileWriter w = new FileWriter(f)) {
            w.write("PORT=5555\n");
        }

        int port = SettingsLoader.loadPortFromFile(f.getAbsolutePath());
        assertEquals(5555, port);

        f.delete();
    }

    @Test
    void testLoadPortMissingFile() {
        int port = SettingsLoader.loadPortFromFile("no_such_file.txt");
        assertEquals(12345, port);
    }

    @Test
    void testLoadPortNoPortEntry() throws Exception {
        File f = new File("test_settings2.txt");
        try (FileWriter w = new FileWriter(f)) {
            w.write("HOST=localhost\n");
        }

        int port = SettingsLoader.loadPortFromFile(f.getAbsolutePath());
        assertEquals(12345, port);

        f.delete();
    }
}
