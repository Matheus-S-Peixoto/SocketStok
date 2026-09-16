package org.studies;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado");

            while(true) {
                System.out.print("INPUT: ");
                String message = scanner.nextLine();

                out.println(message);

                String resposta = in.readLine();
                System.out.println("Servidor: " + resposta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
