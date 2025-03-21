package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import application.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageRoomsController {

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, Number> roomIdColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, Number> rateColumn;

    private ObservableList<Room> rooms = FXCollections.observableArrayList();

    @FXML
    private void navigateToAdminDashboard(ActionEvent event) {
        try {
            Stage stage = (Stage) roomsTable.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml")));
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddRoom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RoomPage.fxml"));
            Scene bookingRoomScene = new Scene(loader.load());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(bookingRoomScene);
            stage.setTitle("Add a Room");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteRoom(ActionEvent event) {
        Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this room?", ButtonType.YES, ButtonType.NO);
            if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                deleteRoomFromDatabase(selectedRoom.getRoomId());
                rooms.remove(selectedRoom);
                System.out.println("Room deleted: " + selectedRoom.getRoomId());
            }
        } else {
            showAlert("No Room Selected", "Please select a room to delete.");
        }
    }

    public void initialize() {
        // Set up table columns
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));

        // Load room data from database
        roomsTable.setItems(fetchRoomsFromDatabase());
    }

    private ObservableList<Room> fetchRoomsFromDatabase() {
        ObservableList<Room> roomList = FXCollections.observableArrayList();

        String query = "SELECT Room_ID, Room_type, Rate FROM Room";
        try (Connection conn = application.DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int roomId = rs.getInt("Room_ID");
                String roomType = rs.getString("Room_type");
                double rate = rs.getDouble("Rate");

                roomList.add(new Room(roomId, roomType, rate, true)); // Availability is not in your schema, defaulting to true
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roomList;
    }

    private void deleteRoomFromDatabase(int roomId) {
        String deleteQuery = "DELETE FROM Room WHERE Room_ID = " + roomId;

        try (Connection conn = application.DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(deleteQuery);
            if (rowsAffected > 0) {
                System.out.println("Room deleted from database: " + roomId);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
