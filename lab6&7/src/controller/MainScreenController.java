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
package controller;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainScreenController {

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TextField searchPartField, searchProductField;

    @FXML
    private void initialize() {
        // Parts Table
        partIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        partNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        partInventoryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        partPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));

        // Products Table
        productIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        productNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        productInventoryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        productPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));

        partsTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());
    }

    @FXML
    private void handleWriteToDB() {
        try (Connection conn = DatabaseManager.connect()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            // Save parts to the database
            for (Part part : Inventory.getAllParts()) {
                Inventory.addPartToDatabase(part);
            }

            // Save products to the database
            for (Product product : Inventory.getAllProducts()) {
                Inventory.addProductToDatabase(product);
            }

            System.out.println("Data successfully written to the database.");
        } catch (Exception e) {
            System.out.println("Error writing to the database: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoadFromDB() {
        try (Connection conn = DatabaseManager.connect()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            Inventory.loadData();
            System.out.println("Data successfully loaded from the database.");
        } catch (Exception e) {
            System.out.println("Error loading data from the database: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Ali\\eclipse-workspace\\Workshop6_7\\inventory_data.ser"))) {
            ArrayList<Part> partsList = new ArrayList<>(Inventory.getAllParts());
            ArrayList<Product> productsList = new ArrayList<>(Inventory.getAllProducts());

            // Write data to the file
            out.writeObject(partsList);
            out.writeObject(productsList);

            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\\\Users\\\\Ali\\\\eclipse-workspace\\\\Workshop6_7\\\\inventory_data.ser"))) {
            // Read data as ArrayLists
            @SuppressWarnings("unchecked")
            ArrayList<Part> partsList = (ArrayList<Part>) in.readObject();
            @SuppressWarnings("unchecked")
            ArrayList<Product> productsList = (ArrayList<Product>) in.readObject();

            ObservableList<Part> parts = FXCollections.observableArrayList(partsList);
            ObservableList<Product> products = FXCollections.observableArrayList(productsList);

            // Set the data back to Inventory
            Inventory.getAllParts().setAll(parts);
            Inventory.getAllProducts().setAll(products);

            System.out.println("Data loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }
    
    
    @FXML
    private void handleAddPart() {
        openWindow("/view/addPart.fxml", "Add Part");
    }

    @FXML
    private void handleModifyPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            showAlert("No Selection", "No Part Selected", "Please select a part to modify.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPart.fxml"));
            Parent root = loader.load();

            ModifyPartController controller = loader.getController();
            controller.setPartData(selectedPart);

            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeletePart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            showAlert("No Selection", "No Part Selected", "Please select a part to delete.");
            return;
        }

        if (showConfirmation("Delete Part", "Are you sure you want to delete this part?", selectedPart.getName())) {
            Inventory.deletePart(selectedPart);
        }
    }

    @FXML
    private void handleAddProduct() {
        openWindow("/view/addProduct.fxml", "Add Product");
    }

    @FXML
    private void handleModifyProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No Selection", "No Product Selected", "Please select a product to modify.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyProduct.fxml"));
            Parent root = loader.load();

            ModifyProductController controller = loader.getController();
            controller.setProductData(selectedProduct);

            Stage stage = new Stage();
            stage.setTitle("Modify Product");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No Selection", "No Product Selected", "Please select a product to delete.");
            return;
        }

        if (showConfirmation("Delete Product", "Are you sure you want to delete this product?", selectedProduct.getName())) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

    @FXML
    private void handleExit() {
        System.out.println("Exiting application...");
        System.exit(0);
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
