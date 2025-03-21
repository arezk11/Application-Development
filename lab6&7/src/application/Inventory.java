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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts() { return allParts; }
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    public static void loadData() {
        try (Connection conn = DatabaseManager.connect()) {
            loadParts(conn);
            loadProducts(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadParts(Connection conn) throws SQLException {
        String query = "SELECT * FROM parts";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory"); // Changed from 'stock' to 'inventory'
                int min = rs.getInt("min");
                int max = rs.getInt("max");
                String type = rs.getString("type");
                if (type.equalsIgnoreCase("InHouse")) {
                    int machineId = rs.getInt("machineId");
                    allParts.add(new InHouse(id, name, price, inventory, min, max, machineId));
                } else if (type.equalsIgnoreCase("Outsourced")) {
                    String companyName = rs.getString("companyName");
                    allParts.add(new Outsourced(id, name, price, inventory, min, max, companyName));
                }
            }
        }
    }

    
    private static void loadProducts(Connection conn) throws SQLException {
        String query = "SELECT * FROM products";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                allProducts.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("inventory"), 
                        rs.getInt("min"),
                        rs.getInt("max")
                ));
            }
        }
    }


    public static void addPartToDatabase(Part part) {
        String query = "INSERT INTO parts (name, price, inventory, min, max, type, machineId, companyName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, part.getName());
            stmt.setDouble(2, part.getPrice());
            stmt.setInt(3, part.getStock()); 
            stmt.setInt(4, part.getMin());
            stmt.setInt(5, part.getMax());
            if (part instanceof InHouse) {
                stmt.setString(6, "InHouse");
                stmt.setInt(7, ((InHouse) part).getMachineId());
                stmt.setNull(8, java.sql.Types.VARCHAR);
            } else if (part instanceof Outsourced) {
                stmt.setString(6, "Outsourced");
                stmt.setNull(7, java.sql.Types.INTEGER);
                stmt.setString(8, ((Outsourced) part).getCompanyName());
            }
            stmt.executeUpdate();
            allParts.add(part);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProductToDatabase(Product product) {
        String query = "INSERT INTO products (name, price, inventory, min, max) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock()); // Changed from 'stock' to 'inventory'
            stmt.setInt(4, product.getMin());
            stmt.setInt(5, product.getMax());
            stmt.executeUpdate();
            allProducts.add(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean deletePart(Part part) {
        return allParts.remove(part);
    }

    public static boolean deleteProduct(Product product) {
        return allProducts.remove(product);
    }

    // Generate unique Product ID
    public static int generateProductId() {
        if (allProducts.isEmpty()) {
            return 1; // Start with 1 if no products exist
        } else {
            int maxId = allProducts.stream()
                    .mapToInt(Product::getId)
                    .max()
                    .orElse(0);
            return maxId + 1;
        }
    }

    // Generate unique Part ID
    public static int generatePartId() {
        if (allParts.isEmpty()) {
            return 1; // Start with 1 if no parts exist
        } else {
            int maxId = allParts.stream()
                    .mapToInt(Part::getId)
                    .max()
                    .orElse(0);
            return maxId + 1;
        }
    }
    
    public static void saveDataToFile(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(new ArrayList<>(allParts));
            out.writeObject(new ArrayList<>(allProducts));
            System.out.println("Data successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static void loadDataFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            @SuppressWarnings("unchecked")
            List<Part> partsList = (List<Part>) in.readObject();
            @SuppressWarnings("unchecked")
            List<Product> productsList = (List<Product>) in.readObject();

            allParts.setAll(partsList);
            allProducts.setAll(productsList);
            System.out.println("Data successfully loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

}
