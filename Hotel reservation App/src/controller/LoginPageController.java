package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();

        // Authenticate the user from the database
        if (authenticateAdmin(username, password)) {
            navigateToAdminDashboard();
        } else {
            showErrorAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticateAdmin(String username, String password) {
        String sql = "SELECT * FROM Admin WHERE Username = ? AND Password = ?";

        try (Connection conn = application.DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Return true if a matching admin is found
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return false;
    }

    private void navigateToAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene adminDashboardScene = new Scene(loader.load());

            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(adminDashboardScene);
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Unable to navigate to the Admin Dashboard.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
