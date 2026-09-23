package org.stok.repository;

import org.junit.jupiter.api.Test;
import org.stok.exceptions.ServiceException;
import org.stok.model.Product;
import org.stok.protocol.Actions;
import org.stok.protocol.request.Request;
import org.stok.protocol.request.RequestBody;

import java.math.BigDecimal;
import java.util.List;

class ProductRepositoryTest {
    private final ProductRepository repo = new ProductRepository();

    @Test
    void createProduct() throws ServiceException {
        RequestBody body = new RequestBody();
        body.setName("Amortecedor Dianteiro da Scania R520");
        body.setDescription("Amortecedor Bosche da Scania R520 2021 StreamLine");
        body.setAmount(new BigDecimal("5321.99"));
        body.setCode("PCCAMSC92007267");

        Product product = repo.createProduct(body);

        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getCode());
    }

    @Test
    void getStok() throws ServiceException {
        List<Product> stok = repo.findAll();

        for (Product product : stok) {
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getAmount());
        }
    }

    @Test
    void updateItem() throws ServiceException {
        RequestBody reqBody = new RequestBody();
        reqBody.setAmount(new BigDecimal("2000"));
        Request req = new Request();
        req.setAction(Actions.P_EDIT);
        req.setId(54);
        req.setBody(reqBody);

        Product product = repo.updateProduct(req);

        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getAmount());
    }

    @Test
    void findById() throws ServiceException {
        Product product = repo.findProductById(54);

        System.out.println(product.getAmount());
    }

}