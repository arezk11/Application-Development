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

public class Outsourced extends Part implements Serializable {
    private static final long serialVersionUID = 1L; // Serializable version UID

    private String companyName;

    public Outsourced(int id, String name, double price, int inventory, int min, int max, String companyName) {
        super(id, name, price, inventory, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
