package com.sergiomartinrubio;

import java.sql.*;

public class ScrollableAndUpdatableResultSet {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            statement.executeUpdate("DROP TABLE IF EXISTS user");

            // Creates SQL table
            statement.executeUpdate("CREATE TABLE user(" +
                    "email VARCHAR(30) NOT NULL PRIMARY KEY," +
                    "name VARCHAR(20) NOT NULL," +
                    "age INTEGER," +
                    "date_time TIMESTAMP" +
                    ")ENGINE=InnoDB;");

            // Creates record in user table
            statement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('econsergio@gmail.com', 'Sergio', 29)");

            // Creates another record in user table
            statement.executeUpdate("INSERT INTO user(email, name, age) " +
                    "VALUES('jose@gmail.com', 'Jose', 61)");

            // Query all records from user table
            try (ResultSet result = statement.executeQuery("SELECT * FROM user")) {
                result.next();
                System.out.println("Row: " + result.getRow());
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getInt(3));
                System.out.println(result.getString(4));
                System.out.println();

                result.next();
                System.out.println("Row: " + result.getRow());
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getInt(3));
                System.out.println(result.getString(4));
                System.out.println();

                // return to previous row
                result.previous();
                System.out.println("Row: " + result.getRow());
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getInt(3));
                System.out.println(result.getString(4));
                System.out.println();

                // Update row 1 result
                System.out.println("Updating row " + result.getRow());
                System.out.println();
                result.updateString("email", "luis@gmail.com");
                result.updateString("name", "Luis");
                result.updateInt("age", 25);
                result.updateRow();

                // go to second row
                result.absolute(2);
                System.out.println("Row: " + result.getRow());
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getInt(3));
                System.out.println(result.getString(4));
                System.out.println();

                // go one row back
                result.relative(-1);
                System.out.println("Row: " + result.getRow());
                System.out.println(result.getString(1));
                System.out.println(result.getString(2));
                System.out.println(result.getInt(3));
                System.out.println(result.getString(4));
                System.out.println();
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

