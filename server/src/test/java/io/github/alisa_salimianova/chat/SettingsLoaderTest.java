package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.chat.server.SettingsLoader;
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

        int port = SettingsLoader.getPort();

        assertEquals(5555, port);
    }
}
