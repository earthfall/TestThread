package org.example2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServer {

    private final ConnectionHandler connectionHandler = new ConnectionHandler();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ExecutorServer(int port) throws IOException {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var socket = serverSocket.accept();

                executorService.submit(() -> connectionHandler.handle(socket));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ExecutorServer(2222);
    }
}
