package io.github.alisa_salimianova.chat;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

public class LoggerUtilTest {

    @Test
    void testLogWritesToFile() throws Exception {
        String testLog = "test-log-entry";
        String logFile = "test.log";

        new File(logFile).delete();

        LoggerUtil.log(logFile, testLog);

        File f = new File(logFile);
        assertTrue(f.exists(), "Log file must be created");

        String content = Files.readString(f.toPath());
        assertTrue(content.contains(testLog));
    }
}
