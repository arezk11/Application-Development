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

public class InHouse extends Part implements Serializable {
    private static final long serialVersionUID = 1L; // Serializable version UID

    private int machineId;

    public InHouse(int id, String name, double price, int inventory, int min, int max, int machineId) {
        super(id, name, price, inventory, min, max);
        this.machineId = machineId;
    }

    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }
}
