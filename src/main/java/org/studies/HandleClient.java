package org.studies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleClient implements Runnable {
    private final Socket client;

    public HandleClient(Socket clientSocket) {
        this.client = clientSocket;
    }

    @Override
    public void run() {
        String nomeThread = Thread.currentThread().getName();

        try (
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(
                    client.getOutputStream(), true)
        ) {
            String message;

            while ((message = in.readLine()) != null) {
                System.out.println("Cliente: " + message + "Thread: " + nomeThread);
                out.println("Echo: " + message);
            }
        } catch (IOException e) {
            System.err.println("[" + nomeThread + "] Erro na comunicação com o cliente: " + e.getMessage());
        } finally {
            // Garante que o socket do cliente seja fechado ao terminar o atendimento
            try {
                client.close();
                System.out.println("[" + nomeThread + "] Conexão com o cliente fechada.");
            } catch (IOException e) {
                System.err.println("Não foi possível fechar o socket do cliente: " + e.getMessage());
            }
        }
    }
}
