package org.stok.server;

import org.stok.exceptions.ProtocolException;
import org.stok.exceptions.ServiceException;
import org.stok.model.Product;
import org.stok.protocol.ProtocolParser;
import org.stok.protocol.ValidateRequest;
import org.stok.protocol.request.Request;
import org.stok.protocol.response.Response;
import org.stok.service.StokService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final StokService service;
    private final ProtocolParser parser;
    private final ValidateRequest validator;

    public ClientHandler(Socket newClient, StokService newService) {
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
                try {
                    Request req = parser.parseRequest(jsonRequest, Request.class);

                    validator.validateRequest(req);

                    Object serviceResult = service.handleRequest(req);

                    Response res = Response.success(req.getAction(), serviceResult);
                    output.println(parser.parseResponse(res));
                } catch (ProtocolException e) {
                    Response res = Response.error(e.getCode(), e.getMessage());
                    output.println(parser.parseResponse(res));
                } catch (ServiceException e) {
                    Response res = Response.error(e.getCode(), e.getMessage());
                    output.println(parser.parseResponse(res));
                }
            }
        } catch (IOException e) {
            System.out.println("Comunication error with client " + e.getMessage());
        }
    }
}
