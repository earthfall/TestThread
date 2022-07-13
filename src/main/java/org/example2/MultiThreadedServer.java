package org.example2;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiThreadedServer {

    private final ConnectionHandler connectionHandler = new ConnectionHandler();

    public MultiThreadedServer(int port) throws IOException {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var socket = serverSocket.accept();

                var thread = new Thread(() -> connectionHandler.handle(socket));
                thread.start();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new MultiThreadedServer(2222);
    }
}
