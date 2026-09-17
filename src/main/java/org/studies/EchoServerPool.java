package org.studies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerPool {
    private static final int PORT = 3030;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("Listening on port: " + PORT);

            while (true) {
                Socket newClient = listener.accept();
                System.out.println();
                System.out.println("Novo cliente: " + newClient.getRemoteSocketAddress());

                threadPool.execute(new HandleClient(newClient));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
