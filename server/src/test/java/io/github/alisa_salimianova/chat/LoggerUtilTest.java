package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.chat.server.LoggerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoggerUtilTest {

    @Test
    void testLogWritesToFile(@TempDir Path tempDir) throws Exception {
        String testLog = "test-log-entry";
        Path logFile = tempDir.resolve("test.log");

        LoggerUtil.log(logFile.toString(), testLog);

        assertTrue(Files.exists(logFile), "Log file must be created");

        String content = Files.readString(logFile);
        assertTrue(content.contains(testLog));
    }
}
