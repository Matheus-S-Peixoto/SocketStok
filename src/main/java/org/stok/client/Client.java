package org.stok.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = Integer.parseInt(System.getenv("SERVER_PORT"));

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(HOST, PORT);

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(),
                        true
                );

                BufferedReader terminal = new BufferedReader(
                        new InputStreamReader(System.in)
                )
        ) {
            System.out.println("Connected to SocketStok server.");
            System.out.println("Type a JSON request or 'exit' to leave.");

            while (true) {
                System.out.print("> ");

                String request = terminal.readLine();

                if (request == null || request.equalsIgnoreCase("exit")) {
                    break;
                }

                output.println(request);

                String response = input.readLine();

                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                System.out.println("< " + response);
            }

        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }
}