package org.stok.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.stok.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(System.getenv("JDBC_URL"));
        config.setUsername(System.getenv("PG_USERNAME"));
        config.setPassword((System.getenv("PG_PASSWORD")));
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static List<Product> fetchData() throws SQLException {
        String query = "SELECT * FROM stok";
        List<Product> products = null;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery();) {
            products = new ArrayList<>();
            Product product;
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setAmount(rs.getBigDecimal("amount"));
                product.setCode(rs.getString("code"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }
}
