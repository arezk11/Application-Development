package controller;

import application.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBookingsController {

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> bookingIdColumn;

    @FXML
    private TableColumn<Booking, String> guestNameColumn;

    @FXML
    private TableColumn<Booking, String> roomTypeColumn;

    @FXML
    private TableColumn<Booking, String> checkInColumn;

    @FXML
    private TableColumn<Booking, String> checkOutColumn;

    @FXML
    private void navigateToAdminDashboard(ActionEvent event) {
        try {
            Stage stage = (Stage) bookingsTable.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml")));
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        // Set up table columns
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        checkInColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        // Populate the table with data from the database
        bookingsTable.setItems(fetchBookingsFromDatabase());
    }

    private ObservableList<Booking> fetchBookingsFromDatabase() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();

        String query = """
            SELECT 
                b.Booking_ID, 
                g.First_name || ' ' || g.Last_name AS GuestName, 
                b.Room_Type, 
                b.Check_In_Date, 
                b.Check_Out_Date
            FROM 
                booking b
            JOIN 
                guest g 
            ON 
                b.Guest_ID = g.Guest_ID;
        """;

        try (Connection conn = application.DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int bookingId = rs.getInt("Booking_ID");
                String guestName = rs.getString("GuestName");
                String roomType = rs.getString("Room_Type");
                String checkInDate = rs.getString("Check_In_Date");
                String checkOutDate = rs.getString("Check_Out_Date");

                bookings.add(new Booking(bookingId, guestName, roomType, checkInDate, checkOutDate));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }
}
