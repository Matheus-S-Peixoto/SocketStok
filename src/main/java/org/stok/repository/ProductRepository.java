package org.stok.repository;

import org.stok.database.DataSource;
import org.stok.model.Product;
import org.stok.protocol.request.RequestBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepository {
    public Product createProduct(RequestBody body) throws SQLException {
        String query = "INSERT INTO stok (name, description, amount, code) VALUES (?, ?, ?, ?) RETURNING id, name, description, amount, code, quantity";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, body.getName());
            pstmt.setString(2, body.getDescription());
            pstmt.setBigDecimal(3, body.getAmount());
            pstmt.setString(4, body.getCode());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();

                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setAmount(rs.getBigDecimal("amount"));
                    product.setCode(rs.getString("code"));
                    product.setQuantity(rs.getInt("quantity"));

                    return product;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new SQLException("Product was not created");
    }
}