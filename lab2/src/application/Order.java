/**********************************************
Workshop 2
Course:APD545 Fall24
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date:2024/10/06
**********************************************/



package application;


import java.util.List;

public class Order {
    // Private members variables for encapsulation
    private String pizzaSize;
    private String crustType;
    private List<String> toppings;
    private int quantity;
    private double totalPrice;

    // Constants prices
    private static final double SMALL_PRICE = 7.00;
    private static final double MEDIUM_PRICE = 10.00;
    private static final double LARGE_PRICE = 13.00;
    private static final double EXTRA_LARGE_PRICE = 15.00;
    private static final double TOPPING_PRICE = 1.10;
    private static final double MEAT_TOPPING_PRICE = 2.15;

    // Constructor
    public Order(String pizzaSize, String crustType, List<String> toppings, int quantity) {
        this.pizzaSize = pizzaSize;
        this.crustType = crustType;
        this.toppings = toppings;
        this.quantity = quantity;
        this.totalPrice = calculateTotal();
    }

    // Getter and Setter methods:
    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
        this.totalPrice = calculateTotal(); 
    }

    public String getCrustType() {
        return crustType;
    }

    public void setCrustType(String crustType) {
        this.crustType = crustType;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
        this.totalPrice = calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = calculateTotal();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Method to calculate total price based on pizza size and the toppings and quantity
    private double calculateTotal() {
        double basePrice = 0.0;

        // Get the price based on the pizza size
        switch (pizzaSize.toLowerCase()) {
            case "small":
                basePrice = SMALL_PRICE;
                break;
            case "medium":
                basePrice = MEDIUM_PRICE;
                break;
            case "large":
                basePrice = LARGE_PRICE;
                break;
            case "extra large":
                basePrice = EXTRA_LARGE_PRICE;
                break;
        }

        // Calculate the total for the toppings
        double toppingsTotal = 0.0;
        for (String topping : toppings) {
            if (isMeatTopping(topping)) {
                toppingsTotal += MEAT_TOPPING_PRICE;
            } else {
                toppingsTotal += TOPPING_PRICE;
            }
        }

        // Calculate the total for the order
        return (basePrice + toppingsTotal) * quantity;
    }

    // Helper method to check if the topping is a meat topping
    private boolean isMeatTopping(String topping) {
        return topping.equalsIgnoreCase("Ground Beef") ||
               topping.equalsIgnoreCase("Shredded Chicken") ||
               topping.equalsIgnoreCase("Grilled Chicken") ||
               topping.equalsIgnoreCase("Pepperoni") ||
               topping.equalsIgnoreCase("Ham") ||
               topping.equalsIgnoreCase("Bacon");
    }

    // Override toString() method to display order details
    @Override
    public String toString() {
        return "Pizza Size: " + pizzaSize + "\n" +
               "Crust Type: " + crustType + "\n" +
               "Toppings: " + String.join(", ", toppings) + "\n" +
               "Quantity: " + quantity + "\n" +
               "Total Price: $" + String.format("%.2f", totalPrice);
    }
}
