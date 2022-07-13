package org.example2;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReactiveServer {

    private final ConnectionHandler connectionHandler = new ConnectionHandler();

    public ReactiveServer(int port) throws IOException {
        try (var serverSocket = new ServerSocket(port)) {
            while (true) {
                var socket = serverSocket.accept();

                Mono.fromRunnable(() -> connectionHandler.handle(socket))
                    .subscribeOn(Schedulers.newSingle("test"))
                    .subscribe();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ReactiveServer(2222);
    }
}
