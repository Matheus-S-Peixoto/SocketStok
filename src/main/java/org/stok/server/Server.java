package org.stok.server;

import org.stok.service.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {
    private static final int PORT = Integer.parseInt(System.getenv("SERVER_PORT"));
    private final Service service;

    public Server(Service service) {
        this.service = service;
    }

    public void start() {
        try (ExecutorService threadPool = newFixedThreadPool(10)) {
            try (ServerSocket listener = new ServerSocket(PORT)) {
                System.out.println("Listening on port: " + PORT);
                //noinspection InfiniteLoopStatement
                while(true) {
                    Socket client = listener.accept();

                    threadPool.execute(new ClientHandler(client, service));
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
