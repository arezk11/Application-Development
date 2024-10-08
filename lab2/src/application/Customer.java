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


public class Customer {
    // Private members variables for encapsulation
    private String name;
    private String phoneNumber;

    // Constructor to initialize the customer details
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Get the name
    public String getName() {
        return name;
    }

    // Set the name
    public void setName(String name) {
        this.name = name;
    }

    // Get the phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Set the phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Override toString() method to display customer information
    @Override
    public String toString() {
        return "Customer Name: " + name + "\n" +
               "Phone Number: " + phoneNumber;
    }
}
