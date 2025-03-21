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

import application.InHouse;
import application.Part;
import application.Outsourced;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class ModifyPartController {

    @FXML
    private RadioButton inHouseRadio;
    
    @FXML
    private RadioButton outsourcedRadio;
    
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
    private Label machineIdLabel;
    
    @FXML
    private TextField machineIdField;
    
    @FXML
    private Label companyNameLabel;
    
    @FXML
    private TextField companyNameField;

    private ToggleGroup partTypeGroup;

    private Part partToModify; // The part to be modified

    public void setPartData(Part part) {
        // Set initial data in the fields
        partToModify = part;
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        inventoryField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        maxField.setText(String.valueOf(part.getMax()));
        minField.setText(String.valueOf(part.getMin()));

        // Set specific fields based on part type
        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            machineIdLabel.setVisible(true);
            machineIdField.setVisible(true);
            machineIdField.setText(String.valueOf(((InHouse) part).getMachineId()));
            companyNameLabel.setVisible(false);
            companyNameField.setVisible(false);
        } else {
            outsourcedRadio.setSelected(true);
            companyNameLabel.setVisible(true);
            companyNameField.setVisible(true);
            companyNameField.setText(((Outsourced) part).getCompanyName());
            machineIdLabel.setVisible(false);
            machineIdField.setVisible(false);
        }
    }

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
        partToModify.setName(nameField.getText());
        partToModify.setStock(Integer.parseInt(inventoryField.getText()));
        partToModify.setPrice(Double.parseDouble(priceField.getText()));
        partToModify.setMax(Integer.parseInt(maxField.getText()));
        partToModify.setMin(Integer.parseInt(minField.getText()));

        if (inHouseRadio.isSelected()) {
            if (partToModify instanceof Outsourced) {
                
            }
            ((InHouse) partToModify).setMachineId(Integer.parseInt(machineIdField.getText()));
        } else {
            if (partToModify instanceof InHouse) {
                
            }
            ((Outsourced) partToModify).setCompanyName(companyNameField.getText());
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
    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
