package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import application.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchGuestsController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Guest> guestsTable;

    @FXML
    private TableColumn<Guest, Integer> guestIdColumn;

    @FXML
    private TableColumn<Guest, String> nameColumn;

    @FXML
    private TableColumn<Guest, String> phoneColumn;

    private ObservableList<Guest> guests = FXCollections.observableArrayList();

    @FXML
    private void navigateToAdminDashboard(ActionEvent event) {
        try {
            Stage stage = (Stage) searchField.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml")));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            showAlert("Invalid Input", "Please enter a name or phone number to search.");
            return;
        }

        // Perform database search
        ObservableList<Guest> filteredGuests = FXCollections.observableArrayList();
        String searchQuery = "SELECT Guest_ID, First_name, Last_name, Phone FROM Guest WHERE " +
                "LOWER(First_name || ' ' || Last_name) LIKE ? OR Phone LIKE ?";
        try (Connection conn = application.DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(searchQuery)) {

            pstmt.setString(1, "%" + query.toLowerCase() + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int guestId = rs.getInt("Guest_ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                String phone = rs.getString("Phone");

                filteredGuests.add(new Guest(guestId, "", firstName, lastName, "", phone, ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while searching the database.");
            return;
        }

        if (filteredGuests.isEmpty()) {
            showAlert("No Results", "No guests found matching the search criteria.");
        } else {
            guestsTable.setItems(filteredGuests);
        }
    }

    public void initialize() {
        // Set up table columns
        guestIdColumn.setCellValueFactory(new PropertyValueFactory<>("guestID"));
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Fetch initial data from database
        guestsTable.setItems(fetchGuestsFromDatabase());
    }

    private ObservableList<Guest> fetchGuestsFromDatabase() {
        ObservableList<Guest> guestList = FXCollections.observableArrayList();

        String query = "SELECT Guest_ID, First_name, Last_name, Phone FROM Guest";
        try (Connection conn = application.DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int guestId = rs.getInt("Guest_ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                String phone = rs.getString("Phone");

                guestList.add(new Guest(guestId, "", firstName, lastName, "", phone, ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return guestList;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
