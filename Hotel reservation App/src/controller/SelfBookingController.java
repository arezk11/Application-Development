package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Room;

import java.time.LocalDate;

public class SelfBookingController {

    @FXML
    private Spinner<Integer> guestsSpinner;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, Integer> roomIdColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, Double> rateColumn;

    @FXML
    private TableColumn<Room, String> availabilityColumn;

    private ObservableList<Room> rooms = FXCollections.observableArrayList();

    @FXML
    private void navigateToMainMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) guestsSpinner.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Main.fxml")));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmBooking(ActionEvent event) {
        Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        Integer guests = guestsSpinner.getValue();
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();

        if (guests == null || checkInDate == null || checkOutDate == null || selectedRoom == null) {
            showAlert("Missing Information", "Please fill in all booking details and select a room.");
            return;
        }

        if (checkOutDate.isBefore(checkInDate)) {
            showAlert("Invalid Dates", "Check-out date cannot be before check-in date.");
            return;
        }

        // Simulate booking confirmation
        showAlert("Booking Confirmed", String.format("Room %d (%s) booked for %d guests from %s to %s.", 
            selectedRoom.getRoomId(), selectedRoom.getRoomType(), guests, checkInDate, checkOutDate));
    }

    public void initialize() {
        // Initialize guests spinner
        guestsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1));

        // Set up table columns
        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().roomIdProperty().asObject());
        roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asObject());
        availabilityColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isAvailable() ? "Available" : "Unavailable"));

        // Load sample rooms
        rooms.addAll(
            new Room(1, "Single", 100.0, true),
            new Room(2, "Double", 150.0, true),
            new Room(3, "Deluxe", 200.0, false)
        );
        roomsTable.setItems(rooms);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
