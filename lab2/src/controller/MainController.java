/**********************************************
Workshop 2
Course: APD545 Fall24
Last Name: Rezk
First Name: Ali
ID: 105593222
Section: ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 2024/10/06
**********************************************/

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    // Customer Information fields
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private ChoiceBox<String> sizeChoiceBox;
    @FXML private ChoiceBox<String> crustChoiceBox;
    @FXML private Label totalPriceBeforeTaxLabel;
    @FXML private Label totalPriceAfterTaxLabel;

    // Topping CheckBoxes
    @FXML private CheckBox pineapple, extraCheese, driedShrimps, mushrooms, anchovies, sunDriedTomatoes, dacon, spinach, roastedGarlic, jalapeno;
    @FXML private CheckBox groundBeef, shreddedChicken, grilledChicken, pepperoni, ham, bacon;

    @FXML private TextField quantityField;

    @FXML
    public void initialize() {
        // Initialize the pizza sizes and the crust types
        sizeChoiceBox.setItems(FXCollections.observableArrayList("Small - $7.00", "Medium - $10.00", "Large - $13.00", "Extra Large - $15.00"));
        crustChoiceBox.setItems(FXCollections.observableArrayList("Normal", "Thin", "Deep Dish"));

       
        // Update the total price when the pizza size changes
        sizeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Update the total price when the quantity changes
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Get all the toppings checkboxes
        List<CheckBox> toppingCheckboxes = getAllToppingCheckboxes();

        // Update the total price when any of the toppings selection changes
        toppingCheckboxes.forEach(checkbox -> checkbox.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice()));

    }

    @FXML
    public void handlePlaceOrder() {
        String customerName = nameField.getText();
        String customerPhone = phoneField.getText();
        String pizzaSize = sizeChoiceBox.getValue();
        String crustType = crustChoiceBox.getValue();
        List<String> selectedToppings = getSelectedToppings();
        int quantityInt = getQuantity();

        double basePrice = getPizzaBasePrice();
        double toppingsPrice = getToppingsPrice();
        double totalPriceBeforeTax = (basePrice + toppingsPrice) * quantityInt;
        double taxRate = 0.13; 
        double totalPriceAfterTax = totalPriceBeforeTax * (1 + taxRate);

        // Create and display the order summary
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n")
               .append("Customer Name: ").append(customerName).append("\n")
               .append("Phone Number: ").append(customerPhone).append("\n")
               .append("Pizza Size: ").append(pizzaSize).append("\n")
               .append("Crust Type: ").append(crustType).append("\n")
               .append("Toppings: ").append(String.join(", ", selectedToppings)).append("\n")
               .append("Quantity: ").append(quantityInt).append("\n")
               .append("Total Price Before Tax: $").append(String.format("%.2f", totalPriceBeforeTax)).append("\n")
               .append("Total Price After Tax: $").append(String.format("%.2f", totalPriceAfterTax)).append("\n");

        showOrderSummary(summary.toString());
    }

    @FXML
    public void handleClearFields() {
        // Clear all input fields and reset the selections
        nameField.clear();
        phoneField.clear();
        sizeChoiceBox.setValue(null);
        crustChoiceBox.setValue(null);
        getAllToppingCheckboxes().forEach(checkbox -> checkbox.setSelected(false));
        quantityField.clear();
    }

    private List<CheckBox> getAllToppingCheckboxes() {
        List<CheckBox> checkboxes = new ArrayList<>();
        // Add each of the toppings checkbox to the list
        checkboxes.add(pineapple);
        checkboxes.add(extraCheese);
        checkboxes.add(driedShrimps);
        checkboxes.add(mushrooms);
        checkboxes.add(anchovies);
        checkboxes.add(sunDriedTomatoes);
        checkboxes.add(dacon);
        checkboxes.add(spinach);
        checkboxes.add(roastedGarlic);
        checkboxes.add(jalapeno);
        checkboxes.add(groundBeef);
        checkboxes.add(shreddedChicken);
        checkboxes.add(grilledChicken);
        checkboxes.add(pepperoni);
        checkboxes.add(ham);
        checkboxes.add(bacon);
        return checkboxes;
    }

    private List<String> getSelectedToppings() {
        List<String> toppings = new ArrayList<>();
        // Add the $1.10 toppings to the list if selected
        if (pineapple.isSelected()) toppings.add("Pineapple");
        if (extraCheese.isSelected()) toppings.add("Extra Cheese");
        if (driedShrimps.isSelected()) toppings.add("Dried Shrimps");
        if (mushrooms.isSelected()) toppings.add("Mushrooms");
        if (anchovies.isSelected()) toppings.add("Anchovies");
        if (sunDriedTomatoes.isSelected()) toppings.add("Sun Dried Tomatoes");
        if (dacon.isSelected()) toppings.add("Dacon");
        if (spinach.isSelected()) toppings.add("Spinach");
        if (roastedGarlic.isSelected()) toppings.add("Roasted Garlic");
        if (jalapeno.isSelected()) toppings.add("Jalapeno");
        
        // Add the $2.15 meat toppings to the list if selected
        if (groundBeef.isSelected()) toppings.add("Ground Beef");
        if (shreddedChicken.isSelected()) toppings.add("Shredded Chicken");
        if (grilledChicken.isSelected()) toppings.add("Grilled Chicken");
        if (pepperoni.isSelected()) toppings.add("Pepperoni");
        if (ham.isSelected()) toppings.add("Ham");
        if (bacon.isSelected()) toppings.add("Bacon");
        return toppings;
    }

    private void updateTotalPrice() {
        double basePrice = getPizzaBasePrice();
        double toppingsPrice = getToppingsPrice();
        int quantity = getQuantity();
        double totalPriceBeforeTax = (basePrice + toppingsPrice) * quantity;
        double taxRate = 0.13; 
        double totalPriceAfterTax = totalPriceBeforeTax * (1 + taxRate);

        totalPriceBeforeTaxLabel.setText(String.format("$%.2f", totalPriceBeforeTax));
        totalPriceAfterTaxLabel.setText(String.format("$%.2f", totalPriceAfterTax));
    }

    private double getPizzaBasePrice() {
        String selectedSize = sizeChoiceBox.getValue();
        if (selectedSize == null) return 0.0;
        switch (selectedSize) {
            case "Small - $7.00": return 7.00;
            case "Medium - $10.00": return 10.00;
            case "Large - $13.00": return 13.00;
            case "Extra Large - $15.00": return 15.00;
            default: return 0.0;
        }
    }

    private double getToppingsPrice() {
        double total = 0.0;
        if (pineapple.isSelected()) total += 1.10;
        if (extraCheese.isSelected()) total += 1.10;
        if (driedShrimps.isSelected()) total += 1.10;
        if (mushrooms.isSelected()) total += 1.10;
        if (anchovies.isSelected()) total += 1.10;
        if (sunDriedTomatoes.isSelected()) total += 1.10;
        if (dacon.isSelected()) total += 1.10;
        if (spinach.isSelected()) total += 1.10;
        if (roastedGarlic.isSelected()) total += 1.10;
        if (jalapeno.isSelected()) total += 1.10;
        if (groundBeef.isSelected()) total += 2.15;
        if (shreddedChicken.isSelected()) total += 2.15;
        if (grilledChicken.isSelected()) total += 2.15;
        if (pepperoni.isSelected()) total += 2.15;
        if (ham.isSelected()) total += 2.15;
        if (bacon.isSelected()) total += 2.15;
        return total;
    }

    private int getQuantity() {
        try {
            return Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private void showOrderSummary(String summary) {
        Stage summaryStage = new Stage();
        summaryStage.setTitle("Order Summary");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        Label summaryLabel = new Label(summary);
        summaryLabel.setWrapText(true);
        vbox.getChildren().add(summaryLabel);
        Scene scene = new Scene(vbox, 400, 300);
        summaryStage.setScene(scene);
        summaryStage.show();
    }
}
