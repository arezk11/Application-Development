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

import application.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddPartController {

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private Label machineIdLabel;

    @FXML
    private TextField machineIdField;

    @FXML
    private Label companyNameLabel;

    @FXML
    private TextField companyNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField inventoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField minField;

    @FXML
    private TextField maxField;

    private ToggleGroup partTypeGroup;

    @FXML
    private void initialize() {
        partTypeGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(partTypeGroup);
        outsourcedRadio.setToggleGroup(partTypeGroup);

        partTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == inHouseRadio) {
                machineIdLabel.setVisible(true);
                machineIdField.setVisible(true);
                companyNameLabel.setVisible(false);
                companyNameField.setVisible(false);
            } else if (newValue == outsourcedRadio) {
                machineIdLabel.setVisible(false);
                machineIdField.setVisible(false);
                companyNameLabel.setVisible(true);
                companyNameField.setVisible(true);
            }
        });
    }

    @FXML
    private void handleSave() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection == null) {
                System.out.println("Failed to connect to the database.");
                return;
            }

            // Collect input data
            String name = nameField.getText();
            int inventory = Integer.parseInt(inventoryField.getText());
            double price = Double.parseDouble(priceField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            // Validate data
            if (min > max || inventory < min || inventory > max) {
                System.out.println("Invalid input: Check min, max, and inventory values.");
                return;
            }

            String sql;
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(machineIdField.getText());
                sql = "INSERT INTO parts (name, inventory, price, min, max, machineId, companyName) VALUES (?, ?, ?, ?, ?, ?, NULL)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, inventory);
                    pstmt.setDouble(3, price);
                    pstmt.setInt(4, min);
                    pstmt.setInt(5, max);
                    pstmt.setInt(6, machineId);
                    pstmt.executeUpdate();
                    System.out.println("In-House part saved to the database.");
                }
            } else {
                String companyName = companyNameField.getText();
                sql = "INSERT INTO parts (name, inventory, price, min, max, machineId, companyName) VALUES (?, ?, ?, ?, ?, NULL, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, inventory);
                    pstmt.setDouble(3, price);
                    pstmt.setInt(4, min);
                    pstmt.setInt(5, max);
                    pstmt.setString(6, companyName);
                    pstmt.executeUpdate();
                    System.out.println("Outsourced part saved to the database.");
                }
            }

            // Close the stage after saving
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            System.out.println("Error saving part: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        // Close the window without saving
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
