package org.example2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.stream.Collectors;

class ConnectionHandler {

    private final MostFrequentWordService mostFrequentWordService = new MostFrequentWordService();

    void handle(Socket socket) {
        System.out.println("Client connected: " + socket);

        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new PrintWriter(socket.getOutputStream(), true)) {
            String line;

            while ((line = in.readLine()) != null) {
                if (isValid(line)) {
                    var wordCount = mostFrequentWordService.mostFrequentWord(line)
                        .stream()
                        .map(counter -> counter.getWord() + ": " + counter.getCount())
                        .collect(Collectors.joining("\n"));
                    out.println(wordCount);
                } else if (line.contains("quit")) {
                    out.println("Goodbye!");
                    socket.close();
                } else {
                    out.println("Malformed URL");
                }
            }
        } catch (IOException e) {
            System.out.println("Was unable to establish or communicate with client socket:" + e.getMessage());
        }
    }

    private static boolean isValid(String stringURL) {
        try {
            new URL(stringURL);
        } catch (MalformedURLException e) {
            System.out.println("invalid url: " + stringURL);
            return false;
        }
        return true;
    }
}
