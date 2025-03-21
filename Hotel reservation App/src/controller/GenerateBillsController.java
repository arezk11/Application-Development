package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

public class GenerateBillsController {

    @FXML
    private ChoiceBox<String> roomChoiceBox;

    public void initialize() {
        // Populate ChoiceBox with sample room data
        ObservableList<String> rooms = FXCollections.observableArrayList("Room 101", "Room 102", "Room 103", "Room 104");
        roomChoiceBox.setItems(rooms);
    }

    @FXML
    private void generateBill(ActionEvent event) {
        String selectedRoom = roomChoiceBox.getValue();

        if (selectedRoom == null) {
            // Show error if no room is selected
            showAlert("Error", "Please select a room to generate a bill.");
        } else {
            // Simulate bill generation logic
            String billDetails = generateBillDetails(selectedRoom);
            showAlert("Bill Generated", "Bill for " + selectedRoom + ":\n\n" + billDetails);
        }
    }

    
    private String generateBillDetails(String room) {
        // Simulated bill details (replace with actual logic if needed)
        return "Room: " + room + "\nAmount: $200.00\nDue Date: 2024-12-20";
    }

    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
