/********************************************** 
Workshop 6&7
Course:APD545 - Fall24
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA 
This assignment represents my own work in accordance with Seneca Academic Policy. 
Signature 
Date:2024-12-5
**********************************************/
package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L; // Serializable version UID

    private transient ObservableList<Part> associatedParts = FXCollections.observableArrayList(); // transient to avoid serialization issues
    private int id;
    private String name;
    private double price;
    private int stock; // Inventory level
    private int min;
    private int max;

    // Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = 0; // Default value
        this.max = Integer.MAX_VALUE; // Default value
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    // Associated Parts Management
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public void addAssociatedParts(ObservableList<Part> parts) {
        associatedParts.addAll(parts);
    }

    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    // Serialization support for the associatedParts
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
        List<Part> serializableParts = new ArrayList<>(associatedParts);
        out.writeObject(serializableParts);
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        List<Part> serializableParts = (List<Part>) in.readObject();
        associatedParts = FXCollections.observableArrayList(serializableParts);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", min=" + min +
                ", max=" + max +
                ", associatedParts=" + associatedParts.size() +
                '}';
    }
}
