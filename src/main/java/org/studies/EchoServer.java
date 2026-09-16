package org.studies;

import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try(ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Listening on port: " + port);
            while(true) {
                Socket clientSocket = listener.accept();
                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleClient(Socket socket) throws IOException {
        try (
            socket;
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Cliente: " + message);
                out.println("Echo: " + message);
            }
        }
    }
}
