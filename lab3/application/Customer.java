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
package application;


public class Customer {

    // Fields for storing customer information
    private String vehicleType;
    private String vehicleAge;
    private String price;
    private String downPayment;
    private String interestRate;

    // Constructor to initialize all fields
    public Customer(String vehicleType, String vehicleAge, String price, String downPayment, String interestRate) {
        this.vehicleType = vehicleType;
        this.vehicleAge = vehicleAge;
        this.price = price;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
    }

    // Getter and Setter methods for each field
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleAge() {
        return vehicleAge;
    }

    public void setVehicleAge(String vehicleAge) {
        this.vehicleAge = vehicleAge;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    // Override the toString() method to display customer information in a readable format
    @Override
    public String toString() {
        return "Vehicle Type: " + vehicleType + ", Vehicle Age: " + vehicleAge + 
               ", Price: $" + price + ", Down Payment: $" + downPayment + 
               ", Interest Rate: " + interestRate;
    }
}
