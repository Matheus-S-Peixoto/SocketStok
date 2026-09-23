package org.stok.repository;

import org.junit.jupiter.api.Test;
import org.stok.model.Product;
import org.stok.protocol.request.RequestBody;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    private final ProductRepository repo = new ProductRepository();

    @Test
    void createProduct() throws SQLException {
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

}