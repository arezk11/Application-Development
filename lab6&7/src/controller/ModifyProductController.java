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

import application.Part;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyProductController {

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
    
    @FXML
    private TextField searchField;


    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private Product product;

    public void setProductData(Product product) {
        this.product = product;

        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inventoryField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        maxField.setText(String.valueOf(product.getMax()));
        minField.setText(String.valueOf(product.getMin()));

        // Use getAllAssociatedParts instead of getAssociatedParts
        associatedParts.setAll(product.getAllAssociatedParts());
    }


    @FXML
    private void initialize() {
        // Initialize the parts table
        partIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        partInventoryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partPriceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        partsTable.setItems(parts);

        // Initialize the associated parts table
        associatedPartIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        associatedPartNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        associatedPartInventoryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        associatedPartPriceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        associatedPartsTable.setItems(associatedParts);
    }

    @FXML
    private void handleAddPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
        }
    }

    @FXML
    private void handleRemovePart() {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.remove(selectedPart);
        }
                                                         
    }

    @FXML
    private void handleSave() {
        // Validate inputs (optional)
        try {
            int inventory = Integer.parseInt(inventoryField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (min > max || inventory < min || inventory > max) {
                System.out.println("Invalid input: Check min, max, and inventory values.");
                return;
            }

            // Update product properties
            product.setName(nameField.getText());
            product.setStock(inventory);
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setMax(max);
            product.setMin(min);

            // Clear and update associated parts
            product.getAllAssociatedParts().clear();
            product.getAllAssociatedParts().addAll(associatedParts);

            System.out.println("Product saved: " + product.getName());
            // Close the window
            closeWindow();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter valid numbers.");
        }
    }
  

    @FXML
    private void handleCancel() {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Unsaved Changes");
        alert.setContentText("Are you sure you want to cancel? Unsaved changes will be lost.");

        // Check user response
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            System.out.println("Modification canceled.");
            closeWindow();
        }
    }

    // Utility method to close the window
    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
