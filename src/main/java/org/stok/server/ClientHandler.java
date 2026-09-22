package org.stok.server;

import org.stok.Protocol.ProtocolException;
import org.stok.Protocol.ProtocolParser;
import org.stok.Protocol.ValidateRequest;
import org.stok.Protocol.request.Request;
import org.stok.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final Service service;
    private final ProtocolParser parser;
    private final ValidateRequest validator;

    public ClientHandler(Socket newClient, Service newService) {
        this.client = newClient;
        this.service = newService;
        this.parser = new ProtocolParser();
        this.validator = new ValidateRequest();
    }

    @Override
    public void run() {
        try (
            client;
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(
                    client.getOutputStream(), true)
        ) {
            String jsonRequest;

            while ((jsonRequest = input.readLine()) != null) {
                Request req = parser.parseRequest(jsonRequest, Request.class);

                validator.validateRequest(req);

            }
        } catch (ProtocolException e) {

        } catch (IOException e) {
            System.out.println("Comunication error with client " + e.getMessage());
        }
    }
}
