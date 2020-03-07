package com.sergiomartinrubio;

import java.sql.*;
import java.util.Arrays;

public class BatchActions {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            statement.executeUpdate("DROP TABLE IF EXISTS user");

            // Turn off auto commit to create a single transaction
            connection.setAutoCommit(false);

            String createTable = "CREATE TABLE user(" +
                    "email VARCHAR(30) NOT NULL PRIMARY KEY," +
                    "name VARCHAR(20) NOT NULL," +
                    "age INTEGER," +
                    "date_time TIMESTAMP" +
                    ")ENGINE=InnoDB;";

            // Add create table statement to batch
            statement.addBatch(createTable);

            String firstInsert = "INSERT INTO user(email, name, age) " +
                    "VALUES('econsergio@gmail.com', 'Sergio', 29)";
            String secondInsert = "INSERT INTO user(email, name, age) " +
                    "VALUES('jose@gmail.com', 'Jose', 61)";

            // Add insert statements to batch
            statement.addBatch(firstInsert);
            statement.addBatch(secondInsert);

            // Commit batch
            int[] counts = statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(false);

            Arrays.stream(counts).forEach(System.out::println);

            // Query all records from user table
            try (ResultSet result = statement.executeQuery("SELECT * FROM user")) {
                while (result.next()) {
                    System.out.println(result.getString(1));
                    System.out.println(result.getString(2));
                    System.out.println(result.getInt(3));
                    System.out.println(result.getString(4));
                }
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
