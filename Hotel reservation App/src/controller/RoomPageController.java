package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomPageController {

    @FXML
    private TextField numberOfGuestsTextField;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private void confirmBooking(ActionEvent event) {
        try {
            // Get user input
            String guestsInput = numberOfGuestsTextField.getText().trim();
            int numberOfGuests = Integer.parseInt(guestsInput);

            // Validate input
            if (numberOfGuests <= 0) {
                showAlert("Invalid Input", "Number of guests must be greater than 0.");
                return;
            }
            if (checkInDatePicker.getValue() == null || checkOutDatePicker.getValue() == null) {
                showAlert("Invalid Input", "Please select both check-in and check-out dates.");
                return;
            }

            // Calculate required rooms
            String roomAllocation = calculateRoomAllocation(numberOfGuests);

            // Redirect to the guest details page with room allocation info
            redirectToGuestDetailsPage(roomAllocation);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for guests.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred.");
        }
    }

    private String calculateRoomAllocation(int numberOfGuests) {
        int singleRooms = 0;
        int doubleRooms = 0;
        int deluxeOrPentRooms = 0;

        // Allocate rooms
        while (numberOfGuests > 0) {
            if (numberOfGuests >= 4) {
                doubleRooms++;
                numberOfGuests -= 4;
            } else if (numberOfGuests >= 2) {
                deluxeOrPentRooms++;
                numberOfGuests -= 2;
            } else {
                singleRooms++;
                numberOfGuests -= 1;
            }
        }

        return "Single Rooms: " + singleRooms +
               "\nDouble Rooms: " + doubleRooms +
               "\nDeluxe/Pent Rooms: " + deluxeOrPentRooms;
    }

    private void redirectToGuestDetailsPage(String roomAllocation) {
        try {
            // Load GuestDetails.fxml and navigate
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GuestDetails.fxml"));
            Scene guestDetailsScene = new Scene(loader.load());

            // Pass room allocation info to GuestDetailsController
            GuestDetailsController controller = loader.getController();
            controller.setRoomAllocationInfo(roomAllocation);

            // Navigate to the GuestDetails page
            Stage stage = (Stage) numberOfGuestsTextField.getScene().getWindow();
            stage.setScene(guestDetailsScene);
            stage.setTitle("Guest Details");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to navigate to the guest details page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
