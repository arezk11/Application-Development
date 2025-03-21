package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Label adminContentLabel;

    @FXML
    private void viewBookings(ActionEvent event) {
        try {
            Stage stage = (Stage) adminContentLabel.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ViewBookings.fxml")));
            stage.setScene(scene);
            stage.setTitle("View Bookings");
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to load the View Bookings page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void manageRooms(ActionEvent event) {
        try {
            Stage stage = (Stage) adminContentLabel.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ManageRooms.fxml")));
            stage.setScene(scene);
            stage.setTitle("Manage Rooms");
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to load the Manage Rooms page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void searchGuests(ActionEvent event) {
        try {
            Stage stage = (Stage) adminContentLabel.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/SearchGuests.fxml")));
            stage.setScene(scene);
            stage.setTitle("Search Guests");
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to load the Search Guests page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void generateBills(ActionEvent event) {
        try {
            Stage stage = (Stage) adminContentLabel.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/GenerateBills.fxml")));
            stage.setScene(scene);
            stage.setTitle("Generate Bills");
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to load the Generate Bills page.");
            e.printStackTrace();
        }
    }

    @FXML
    private void exitToMain(ActionEvent event) {
        try {
            Stage stage = (Stage) adminContentLabel.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Main.fxml")));
            stage.setScene(scene);
            stage.setTitle("Main Menu");
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to navigate to the Main Menu.");
            e.printStackTrace();
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
