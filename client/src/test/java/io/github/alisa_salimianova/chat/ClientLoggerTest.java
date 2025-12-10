package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.client.ClientLogger;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientLoggerTest {

    private Path tempDir;
    private Path logFilePath;
    private ClientLogger logger;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("loggerTest");
        logFilePath = tempDir.resolve("client.log");
        logger = new ClientLogger(logFilePath.toString());
    }

    @Test
    void testLogFileCreated() {
        assertTrue(Files.exists(logFilePath),
                "Log file should be created");
    }

    @Test
    void testLogMessageAppended() throws IOException {
        logger.log("Hello");
        logger.log("World");

        String content = Files.readString(logFilePath);
        assertTrue(content.contains("Hello"));
        assertTrue(content.contains("World"));
    }

    @Test
    void testLogFormatContainsTimestamp() throws IOException {
        logger.log("Test message");

        String content = Files.readString(logFilePath);
        assertTrue(content.matches("(?s).*\\[\\d{4}-\\d{2}-\\d{2} .* Test message.*"),
                "Log must contain timestamp in square brackets");
    }
}
