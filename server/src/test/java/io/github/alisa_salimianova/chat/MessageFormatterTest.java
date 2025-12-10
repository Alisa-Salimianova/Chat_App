package io.github.alisa_salimianova.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageFormatterTest {

    @Test
    void testMessageFormatting() {
        String formatted = MessageFormatter.format("Bob", "Hello");
        assertEquals("Bob: Hello", formatted);
    }
}
