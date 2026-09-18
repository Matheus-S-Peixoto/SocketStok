package org.studies;

import java.sql.*;
import java.util.List;

import static org.studies.DataSource.fetchData;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Item> produtos = fetchData();

        for(Item item : produtos) {
            System.out.println(item.getNome());
        }
    }
}