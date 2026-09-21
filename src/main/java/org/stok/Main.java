package org.stok;

import org.stok.model.Product;

import java.sql.SQLException;
import java.util.List;

import static org.stok.database.DataSource.fetchData;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Product> products = fetchData();

        for (Product product : products) {
            System.out.println(product.getName());
            System.out.println(product.getCode());
            System.out.println("\n\n");
        }
    }
}
