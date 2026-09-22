package org.stok.server;

import org.stok.service.Service;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final Service service;

    public ClientHandler(Socket newClient, Service newService) {
        this.client = newClient;
        this.service = newService;
    }

    @Override
    public void run() {

    }
}
