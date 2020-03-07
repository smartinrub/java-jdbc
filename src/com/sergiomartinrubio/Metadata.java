package com.sergiomartinrubio;

import java.sql.*;

public class Metadata {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS user");

            // Creates SQL table
            statement.executeUpdate("CREATE TABLE user(" +
                    "email VARCHAR(30) NOT NULL," +
                    "name VARCHAR(20) NOT NULL," +
                    "age INTEGER," +
                    "date_time TIMESTAMP" +
                    ")ENGINE=InnoDB;");

            // Creates record in user table
            statement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('econsergio@gmail.com', 'Sergio', 29)");

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                // This returns all table names
                System.out.println(resultSet.getString(3));
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }
}
