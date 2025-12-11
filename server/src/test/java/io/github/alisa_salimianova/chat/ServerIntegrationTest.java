package io.github.alisa_salimianova.chat;

import io.github.alisa_salimianova.chat.server.ChatServer;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ServerIntegrationTest {

    private static final int PORT = 34567;
    private ChatServer server;
    private Thread serverThread;

    @BeforeEach
    void startServer() {
        server = new ChatServer(PORT);
        serverThread = server.startAsync();
    }

    @AfterEach
    void stopServer() {
        server.stop();
        try {
            serverThread.join(200);
        } catch (InterruptedException ignored) {}
    }

    @Test
    void testClientConnects() throws Exception {
        try (Socket socket = new Socket("localhost", PORT)) {
            assertTrue(socket.isConnected(), "Клиент должен подключиться");
        }
    }

    @Test
    void testServerBroadcastsMessage() throws Exception {
        Socket a = new Socket("localhost", PORT);
        BufferedWriter outA = new BufferedWriter(new OutputStreamWriter(a.getOutputStream(), StandardCharsets.UTF_8));

        Socket b = new Socket("localhost", PORT);
        BufferedReader inB = new BufferedReader(new InputStreamReader(b.getInputStream(), StandardCharsets.UTF_8));

        String msg = "Hello from A";
        outA.write(msg + "\n");
        outA.flush();

        String received = inB.readLine();

        assertEquals(msg, received, "Клиент B должен получить сообщение клиента A");

        a.close();
        b.close();
    }

    @Test
    void testClientReceivesMessage() throws Exception {
        Socket a = new Socket("localhost", PORT);
        BufferedReader inA = new BufferedReader(new InputStreamReader(a.getInputStream(), StandardCharsets.UTF_8));

        Socket b = new Socket("localhost", PORT);
        BufferedWriter outB = new BufferedWriter(new OutputStreamWriter(b.getOutputStream(), StandardCharsets.UTF_8));

        String msg = "Test message 123";
        outB.write(msg + "\n");
        outB.flush();

        String line = inA.readLine();

        assertEquals(msg, line, "Клиент A должен получить сообщение от B");

        a.close();
        b.close();
    }
}
