package org.stok.server;

import org.stok.service.StokService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {
    private static final int PORT = Integer.parseInt(System.getenv("SERVER_PORT"));
    private final StokService service;

    public Server(StokService service) {
        this.service = service;
    }

    public void start() {
        try (ServerSocket listener = new ServerSocket(PORT);
             ExecutorService threadPool = newFixedThreadPool(10)) {

            System.out.println("Listening on port: " + PORT);

            while(true) {
                Socket client = listener.accept();

                threadPool.execute(new ClientHandler(client, service));
            }

        } catch(IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}
