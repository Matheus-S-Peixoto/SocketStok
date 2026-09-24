package org.stok;

import org.stok.repository.ProductRepository;
import org.stok.server.Server;
import org.stok.service.StokService;

public class SocketServerMain {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        StokService stokService = new StokService(productRepository);

        Server socketStokServer = new Server(stokService);

        socketStokServer.start();
    }
}
