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

import application.Customer;
import application.LoanData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CarloanController {

    // Fields for customer input and vehicle details
    @FXML
    private ComboBox<String> vehicleTypeComboBox;

    @FXML
    private ComboBox<String> vehicleAgeComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private TextField downPaymentField;

    @FXML
    private TextField customInterestRateField;

    @FXML
    private ToggleGroup interestRateGroup;

    @FXML
    private Label calculationResultLabel;

    @FXML
    private RadioButton interestRate1;

    @FXML
    private RadioButton interestRate2;

    @FXML
    private RadioButton interestRate3;

    @FXML
    private RadioButton otherInterestRadio;

    @FXML
    private ComboBox<String> paymentFrequencyComboBox;

    @FXML
    private Slider loanDurationSlider;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField cityField;

    @FXML
    private ComboBox<String> provinceComboBox;

    // Instance of LoanData
    private LoanData loanData;

    // Fields for storing customer data
    private List<Customer> customerList = new ArrayList<>();
    private ObservableList<Customer> observableCustomerList = FXCollections.observableArrayList();

    // Initialize method to set up ComboBox and other UI elements
    @FXML
    public void initialize() {
        // Initialize the LoanData object
        loanData = new LoanData();

        if (interestRateGroup == null) {
            interestRateGroup = new ToggleGroup();
        }
        // Set interest rates to the toggle group
        RadioButton[] interestRateButtons = {interestRate1, interestRate2, interestRate3, otherInterestRadio};
        for (RadioButton rb : interestRateButtons) {
            rb.setToggleGroup(interestRateGroup);
        }

        // Add listener for the "Other" interest rate RadioButton
        otherInterestRadio.selectedProperty().addListener((observable, oldValue, newValue) -> {
            customInterestRateField.setVisible(newValue); // Show the custom field if "Other" is selected
        });

        // Populate ComboBoxes
        provinceComboBox.setItems(FXCollections.observableArrayList(
            "Alberta","Ontario","Quebec" ));
       

        vehicleTypeComboBox.setItems(FXCollections.observableArrayList(
            "Car", "Truck", "Family Van"
        ));

        vehicleAgeComboBox.setItems(FXCollections.observableArrayList(
            "New", "Used"
        ));

        paymentFrequencyComboBox.setItems(FXCollections.observableArrayList(
            "Weekly", "Bi-weekly", "Monthly"
        ));

        // Hide the custom interest rate field initially
        customInterestRateField.setVisible(false);
    }

    // Method to validate user information
    private boolean validateUserInfo() {
        // Validate the customer's name - only letters and spaces
        if (nameField.getText().trim().isEmpty() || !nameField.getText().matches("[a-zA-Z\\s]+")) {
            showAlert("Name must only contain letters and cannot be empty!", nameField);
            return false;
        }

        // Validate the phone number - only digits
        if (phoneField.getText().trim().isEmpty() || !phoneField.getText().matches("\\d+")) {
            showAlert("Phone must only contain numbers and cannot be empty!", phoneField);
            return false;
        }

        // Validate the city - only letters and spaces
        if (cityField.getText().trim().isEmpty() || !cityField.getText().matches("[a-zA-Z\\s]+")) {
            showAlert("City must only contain letters and cannot be empty!", cityField);
            return false;
        }

        // Validate the province - must be selected
        if (provinceComboBox.getValue() == null) {
            showAlert("Please select a province!", provinceComboBox);
            return false;
        }

        // Validate the vehicle type - must be selected
        if (vehicleTypeComboBox.getValue() == null) {
            showAlert("Please select a vehicle type!", vehicleTypeComboBox);
            return false;
        }

        // Validate the vehicle age - must be selected
        if (vehicleAgeComboBox.getValue() == null) {
            showAlert("Please select the age of the vehicle!", vehicleAgeComboBox);
            return false;
        }

        // Validate the price of the vehicle - must be a number
        if (priceField.getText().trim().isEmpty() || !priceField.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Price must be a valid number and cannot be empty!", priceField);
            return false;
        }

        // Validate the down payment - must be a number
        if (downPaymentField.getText().trim().isEmpty() || !downPaymentField.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Down payment must be a valid number and cannot be empty!", downPaymentField);
            return false;
        }

        // Validate the interest rate - must be selected
        if (interestRateGroup.getSelectedToggle() == null) {
            showAlert("Please select an interest rate!", null);
            return false;
        }

        // Validate the custom interest rate if 'Other' is selected
        if (otherInterestRadio.isSelected() && (customInterestRateField.getText().trim().isEmpty() || !customInterestRateField.getText().matches("\\d+(\\.\\d+)?"))) {
            showAlert("Please enter a valid custom interest rate!", customInterestRateField);
            return false;
        }

        // Validate payment frequency - must be selected
        if (paymentFrequencyComboBox.getValue() == null) {
            showAlert("Please select a payment frequency!", paymentFrequencyComboBox);
            return false;
        }

        return true;
    }


    
    private void showAlert(String message, Control controlToFocus) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        if (controlToFocus != null) {
            controlToFocus.requestFocus();
        }
    }

    // Method to handle the calculation logic
    @FXML
    private void handleCalculate() {
        if (validateUserInfo()) {
            try {
                // Get the input values
                double price = Double.parseDouble(priceField.getText().trim());
                double downPayment = Double.parseDouble(downPaymentField.getText().trim());
                double principal = price - downPayment;

                // Get the loan duration from the slider
                int loanDurationMonths = (int) loanDurationSlider.getValue();

                // Get the interest rate
                double annualInterestRate;
                if (otherInterestRadio.isSelected()) {
                    annualInterestRate = Double.parseDouble(customInterestRateField.getText().trim()) / 100;
                } else {
                    RadioButton selectedRadioButton = (RadioButton) interestRateGroup.getSelectedToggle();
                    annualInterestRate = Double.parseDouble(selectedRadioButton.getText().replace("%", "")) / 100;
                }

                // Convert annual interest rate to monthly interest rate
                double monthlyInterestRate = annualInterestRate / 12;

                // Calculate the monthly payment using the loan formula
                double monthlyPayment;
                if (monthlyInterestRate == 0) {
                    // If interest rate is 0, calculate a simple division
                    monthlyPayment = principal / loanDurationMonths;
                } else {
                    // Use the loan payment formula
                    monthlyPayment = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanDurationMonths)) /
                                     (Math.pow(1 + monthlyInterestRate, loanDurationMonths) - 1);
                }

                // Adjust the payment based on the payment frequency
                String paymentFrequency = paymentFrequencyComboBox.getValue();
                double finalPayment;
                String frequencyLabel = "";

                if ("Weekly".equals(paymentFrequency)) {
                    finalPayment = monthlyPayment / 4.33;  
                    frequencyLabel = "Weekly";
                } else if ("Bi-weekly".equals(paymentFrequency)) {
                    finalPayment = monthlyPayment / 2;  
                    frequencyLabel = "Bi-weekly";
                } else {
                    finalPayment = monthlyPayment;  
                    frequencyLabel = "Monthly";
                }

                // Display the result in the label
                calculationResultLabel.setText(String.format("%s Payment: $%.2f", frequencyLabel, finalPayment));

            } catch (NumberFormatException e) {
                calculationResultLabel.setText("Please enter valid numbers");
                System.out.println("Invalid number format encountered.");
            }
        }
    }


    // Method to save customer information
    @FXML
    private void handleSave() {
        if (validateUserInfo()) {
            // Create a new Customer instance
            Customer customer = new Customer(
                vehicleTypeComboBox.getValue(),
                vehicleAgeComboBox.getValue(),
                priceField.getText().trim(),
                downPaymentField.getText().trim(),
                getSelectedInterestRate()
            );

            // Save the customer to the list and update the observable list
            customerList.add(customer);
            observableCustomerList.add(customer);

        }
    }

    // Helper method to get the selected interest rate
    private String getSelectedInterestRate() {
        if (otherInterestRadio.isSelected()) {
            return customInterestRateField.getText().trim();
        } else {
            RadioButton selectedRadioButton = (RadioButton) interestRateGroup.getSelectedToggle();
            return selectedRadioButton.getText();
        }
    }

    // Method to clear the form fields
    @FXML
    private void handleClear() {
        // Clear customer information fields
        nameField.clear();
        phoneField.clear();
        cityField.clear();
        provinceComboBox.getSelectionModel().clearSelection();

        // Clear vehicle and loan information fields
        vehicleTypeComboBox.getSelectionModel().clearSelection();
        vehicleAgeComboBox.getSelectionModel().clearSelection();
        priceField.clear();
        downPaymentField.clear();
        customInterestRateField.clear();
        interestRateGroup.selectToggle(null);
        customInterestRateField.setVisible(false);
        paymentFrequencyComboBox.getSelectionModel().clearSelection();
        loanDurationSlider.setValue(12); // Reset the slider to the minimum value
    }

    // Method to show saved rates (populate the ListView)
    @FXML
    private void handleShowSavedRates() {
        if (customerList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Saved Customers");
            alert.setHeaderText(null);
            alert.setContentText("There are no saved customers to display.");
            alert.showAndWait();
            return;
        }

        // Create a dialog to display saved rates
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Saved Rates");

        // Create a ListView to display the saved customers
        ListView<Customer> listView = new ListView<>();
        listView.setItems(observableCustomerList);

        // Set up the dialog content with a VBox layout
        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(listView);
        dialogContent.setPrefSize(500, 400); // Set preferred size of the dialog

        // Set the content of the dialog
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(dialogContent);

        // Add an OK button to close the dialog
        dialogPane.getButtonTypes().add(ButtonType.OK);

        // Add listener to handle selection
        listView.setOnMouseClicked(event -> {
            Customer selectedCustomer = listView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                // Populate form with selected customer's data
                vehicleTypeComboBox.setValue(selectedCustomer.getVehicleType());
                vehicleAgeComboBox.setValue(selectedCustomer.getVehicleAge());
                priceField.setText(selectedCustomer.getPrice());
                downPaymentField.setText(selectedCustomer.getDownPayment());
                dialog.close(); // Close the dialog after selection
            }
        });

        // Show the dialog
        dialog.showAndWait();
    }
}
