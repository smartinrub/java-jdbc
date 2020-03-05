package com.sergiomartinrubio;

import java.sql.*;

public class PreparedStatementQueries {

    public static void main(String[] args) throws SQLException {
        String selectByEmail = "SELECT * FROM user WHERE name = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectByEmail)) {

            // Creates SQL table
            preparedStatement.executeUpdate("CREATE TABLE user(" +
                    "email VARCHAR(30) NOT NULL," +
                    "name VARCHAR(20) NOT NULL," +
                    "age INTEGER," +
                    "date_time TIMESTAMP" +
                    ")ENGINE=InnoDB;");

            // Creates record in user table
            preparedStatement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('econsergio@gmail.com', 'Sergio', 29)");

            // Set a string to a user name
            preparedStatement.setString(1, "Sergio");

            // Query records from user table by user
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    System.out.println(result.getString("email"));
                    System.out.println(result.getString("name"));
                    System.out.println(result.getInt("age"));
                    System.out.println(result.getString("date_time"));
                }
            }

            // Deletes user table
            preparedStatement.executeUpdate("DROP TABLE user");
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }
}
