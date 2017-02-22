package com.glispa.testassessment.server;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;

public class Server {
    private MockWebServer server;
    private boolean running = false;

    private Server() {
        server = new MockWebServer();
        server.setDispatcher(new ResponseDispatcher());
    }

    public static Server getInstance() {
        return ServerHolder.INSTANCE;
    }

    public void start() throws IOException {
        if (!running) {
            running = true;
            server.start(8081);
        }
    }

    public void stop() throws IOException {
        server.shutdown();
    }

    public HttpUrl getUrl() {
        return server.url("/");
    }

    private static class ServerHolder {
        private static final Server INSTANCE = new Server();
    }
}
