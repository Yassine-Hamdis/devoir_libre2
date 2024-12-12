package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private  final String URL = "jdbc:mysql://localhost:3306/devoirlibre2";
    private  final String USERNAME = "root";
    private  final String PASSWORD = "root";


    public DatabaseConnection() {}


    public Connection getConnection() throws SQLException {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);


            return connection;
        } catch (ClassNotFoundException e) {
           

            throw new SQLException("Database driver not found", e);
        }
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
