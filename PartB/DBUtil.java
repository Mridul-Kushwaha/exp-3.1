package com.example.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // Update DB name, username, password if needed
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/company?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";      // your MySQL username
    private static final String DB_PASS = "password";  // your MySQL password

    // Load MySQL JDBC Driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found!", e);
        }
    }

    // Return a connection object
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
