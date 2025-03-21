/********************************************** 
Workshop 6&7
Course:APD545 - Fall24
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA 
This assignment represents my own work in accordance with Seneca Academic Policy. 
Signature 
Date:2024-12-5
**********************************************/

package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String URL = "jdbc:sqlite:C:\\Users\\Ali\\eclipse-workspace\\Workshop6_7\\inventory.db";
    private static final String DRIVER = "org.sqlite.JDBC";

    /**
     * Get a connection to the database.
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER); // Load SQLite driver
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            System.out.println("Database Driver not found: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Close the given database connection.
     * @param connection Connection to be closed
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
