package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button bookRoomButton;
    @FXML
    private Button adminLoginButton;  

    @FXML
    public void navigateToRoomPage() {
        try {
            // Load RoomPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RoomPage.fxml"));
            Scene roomPageScene = new Scene(loader.load());

            Stage stage = (Stage) bookRoomButton.getScene().getWindow();
            stage.setScene(roomPageScene);
            stage.setTitle("Room Booking");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToAdminLogin() {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage stage = (Stage) adminLoginButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Admin Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitApplication() {
        System.exit(0);
    }
}
