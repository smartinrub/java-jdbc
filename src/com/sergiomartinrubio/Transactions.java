package com.sergiomartinrubio;

import java.sql.*;

public class Transactions {

    public static void main(String[] args) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Creates SQL table
            statement.executeUpdate("CREATE TABLE user(" +
                    "email VARCHAR(30) NOT NULL," +
                    "name VARCHAR(20) NOT NULL," +
                    "age INTEGER," +
                    "date_time TIMESTAMP" +
                    ")ENGINE=InnoDB;");

            connection.setAutoCommit(false);

            // Creates record in user table
            statement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('econsergio@gmail.com', 'Sergio', 29)");

            // Creates another record in user table
            statement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('jose@gmail.com', 'Jose', 61)");
            connection.commit();

            connection.setAutoCommit(true);

            // Query all records from user table
            try (ResultSet result = statement.executeQuery("SELECT * FROM user")) {
                while (result.next()) {
                    System.out.println(result.getString(1));
                    System.out.println(result.getString(2));
                    System.out.println(result.getInt(3));
                    System.out.println(result.getString(4));
                }
            }

            // Deletes user table
            statement.executeUpdate("DROP TABLE user");

            SQLWarning warning = statement.getWarnings();
            while (warning != null) {
                System.out.println(warning.getNextWarning());
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getSQLState());
            System.out.println(sqlException.getErrorCode());
            for (Throwable throwable : sqlException) {
                System.out.println(throwable.getMessage());
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
