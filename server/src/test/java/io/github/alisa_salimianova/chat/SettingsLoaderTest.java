package io.github.alisa_salimianova.chat;

import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsLoaderTest {

    @Test
    void testSettingsLoaderReadsPort() throws Exception {
        String file = "test_settings.txt";
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("PORT=5555");
        }

        int port = SettingsLoader.loadPort(file);

        assertEquals(5555, port);
    }
}
