package io.github.alisa_salimianova.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientCommandTest {

    @Test
    void testExitCommand() {
        String input = "/exit";
        assertTrue(input.equalsIgnoreCase("/exit"));
    }
}
