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

import application.Inventory;
import application.Part;
import application.Product;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField inventoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField searchField;

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
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        partIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        partNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        partInventoryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        partPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        associatedPartIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        associatedPartNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        associatedPartInventoryColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStock()));
        associatedPartPriceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        partsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(associatedParts);
    }

    @FXML
    private void handleAddPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            System.out.println("Part added: " + selectedPart.getName());
        } else {
            showError("No part selected", "Please select a part to associate.");
        }
    }

    @FXML
    private void handleRemovePart() {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.remove(selectedPart);
            System.out.println("Part removed: " + selectedPart.getName());
        } else {
            showError("No part selected", "Please select a part to remove.");
        }
    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText();
            if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");

            int stock = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(priceField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (min > max) throw new IllegalArgumentException("Minimum value cannot be greater than maximum.");
            if (stock < min || stock > max) throw new IllegalArgumentException("Inventory must be between min and max.");
            if (associatedParts.isEmpty()) throw new IllegalArgumentException("Product must have at least one associated part.");
            Product newProduct = new Product(Inventory.generateProductId(), name, price, stock, min, max);
            newProduct.addAssociatedParts(associatedParts);
            Inventory.addProductToDatabase(newProduct);

            System.out.println("Product saved: " + name);
            closeWindow();
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter valid numbers in the fields.");
        } catch (IllegalArgumentException e) {
            showError("Input Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        if (showConfirmation("Cancel Confirmation", "Are you sure you want to cancel? Unsaved changes will be lost.")) {
            closeWindow();
        }
    }

    // Helper methods
    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
