package org.studies;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
        config.setPassword(System.getenv("PG_PASSWORD"));
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static List<Item> fetchData() throws SQLException {
        String query = "SELECT * FROM catalogo";
        List<Item> itens = null;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery();) {
            itens = new ArrayList<>();
            Item item;
            while (rs.next()) {
                item = new Item();
                item.setID(rs.getInt("id"));
                item.setNome(rs.getString("produto"));
                item.setFamilia_id(rs.getInt("familia_id"));
                item.setPeso(rs.getFloat("peso"));
                item.setTipo(rs.getString("tipo"));
                item.setAnimal(rs.getString("animal"));
                itens.add(item);
            }
        }
        return itens;
    }
}
