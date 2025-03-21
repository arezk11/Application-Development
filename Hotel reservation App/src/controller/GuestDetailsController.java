package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuestDetailsController {

    @FXML
    private TextField guestFirstNameTextField;

    @FXML
    private TextField guestLastNameTextField;

    @FXML
    private TextField guestPhoneTextField;

    @FXML
    private Label roomAllocationLabel;

    // Method to set room allocation info
    public void setRoomAllocationInfo(String roomAllocationInfo) {
        roomAllocationLabel.setText(roomAllocationInfo);
    }

    @FXML
    private void confirmGuestDetails() {
        String firstName = guestFirstNameTextField.getText();
        String lastName = guestLastNameTextField.getText();
        String phone = guestPhoneTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            System.out.println("Please fill in all required fields.");
            return;
        }

        saveGuestDetails(firstName, lastName, phone);
    }

    private void saveGuestDetails(String firstName, String lastName, String phone) {
        String sql = "INSERT INTO Guest (First_name, Last_name, Phone) VALUES (?, ?, ?)";

        try (Connection conn = application.DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Guest details saved successfully.");
            } else {
                System.out.println("Failed to save guest details.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
