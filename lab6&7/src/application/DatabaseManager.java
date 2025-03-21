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

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\Ali\\eclipse-workspace\\Workshop6_7\\inventory.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
