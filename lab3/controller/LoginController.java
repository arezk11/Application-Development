/**********************************************
Workshop 3
Course:APD545 Fall24
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date:2024/10/27
**********************************************/
package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    @FXML
    private Button btnLogin;

    // Hardcoded login info
    private final String USERNAME = "aaa";
    private final String PASSWORD = "123";
    

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            // Load the main application window upon successful login
            try {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/View/Carloan.fxml")));
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Show error message
            lblError.setText("Invalid username or password");
        }
    }
}
