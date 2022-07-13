package org.example2;

import java.io.IOException;
import java.net.ServerSocket;

public class SingleThreadedServer {
    private final ConnectionHandler connectionHandler = new ConnectionHandler();

    public SingleThreadedServer(int port) throws IOException {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var socket = serverSocket.accept();
                connectionHandler.handle(socket);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SingleThreadedServer(2222);
    }
}