package org.example.billing_system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static DBConn instance;

    private DBConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Singleton Design Patterns
    public static synchronized DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // Test the connection
            if (conn != null && !conn.isClosed()) {
                return conn;
            } else {
                throw new SQLException("Failed to establish database connection");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            throw new SQLException("Unable to connect to database. Please check if MySQL is running and the database exists.", e);
        }
    }
}
