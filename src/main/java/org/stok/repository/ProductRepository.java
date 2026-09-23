package org.stok.repository;

import org.stok.database.DataSource;
import org.stok.exceptions.ResponseCodes;
import org.stok.exceptions.ServiceException;
import org.stok.model.Product;
import org.stok.protocol.request.Request;
import org.stok.protocol.request.RequestBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public Product createProduct(RequestBody body) throws ServiceException {
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
                throw new ServiceException(
                        ResponseCodes.INTERNAL_ERROR,
                        "Product was not created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getStok() {
        String query = "SELECT * FROM stok";
        List<Product> productList;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            productList = new ArrayList<>();
            Product product;
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setAmount(rs.getBigDecimal("amount"));
                product.setCode(rs.getString("code"));
                product.setQuantity(rs.getInt("quantity"));
                productList.add(product);
            }

            return productList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product findProductById(Integer id) throws ServiceException {
        String query = "SELECT id, name, description, amount, code, quantity FROM stok WHERE id = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

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
                throw new ServiceException(
                        ResponseCodes.NOT_FOUND,
                        "Could not find a product with the id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(Request req) throws ServiceException {
        String query = "UPDATE stok SET name = COALESCE(?, name), description = COALESCE(?, description), amount = COALESCE(?, amount), code = COALESCE(?, code), quantity = COALESCE(?, quantity) WHERE id = ? RETURNING id, name, description, amount, code, quantity";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, req.getBody().getName());
            pstmt.setString(2, req.getBody().getDescription());
            pstmt.setBigDecimal(3, req.getBody().getAmount());
            pstmt.setString(4, req.getBody().getCode());
            pstmt.setInt(5, req.getBody().getQuantity());
            pstmt.setInt(6, req.getId());

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
                throw new ServiceException(
                        ResponseCodes.NOT_FOUND,
                        "Could not find a product with the id: " + req.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product deleteProduct(Integer id) throws ServiceException {
        String query = "DELETE FROM stok WHERE id = ? RETURNING id, name, description, amount, code, quantity";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

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
                throw new ServiceException(
                        ResponseCodes.NOT_FOUND,
                        "Could not find a product with the id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product incrementQuantity(Integer id, Integer quantity) throws ServiceException {
        String query = "UPDATE stok SET quantity = quantity + ? WHERE id = ? RETURNING id, name, quantity";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();

                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));

                    return product;
                }
                throw new ServiceException(
                        ResponseCodes.NOT_FOUND,
                        "Could not find a product with the id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product decrementQuantity(Integer id, Integer quantity) throws ServiceException {
        String query = "UPDATE stok SET quantity = quantity - ? WHERE id = ? RETURNING id, name, quantity";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();

                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));

                    return product;
                }
                throw new ServiceException(
                        ResponseCodes.NOT_FOUND,
                        "Could not find a product with the id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getQuantityById(Integer id) throws ServiceException {
        String query = "SELECT quantity FROM stok WHERE id = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getObject("quantity", Integer.class);
                }
            }
            throw new ServiceException(
                    ResponseCodes.NOT_FOUND,
                    "Could not find a product with the id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}