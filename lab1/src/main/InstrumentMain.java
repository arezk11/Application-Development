/**********************************************
Workshop 1
Course:APD545 - Fall
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date:2024/09/22
**********************************************/

package main;

import controller.InstrumentController;
import view.InstrumentView; // Import the view
import java.util.Scanner;

public class InstrumentMain {
    public static void main(String[] args) {
        // Create a Scanner object to take the user input
        Scanner scanner = new Scanner(System.in);
        
        // Create the view object
        InstrumentView view = new InstrumentView();
        
        // Pass the view to the controller
        InstrumentController controller = new InstrumentController(view);

        // Input prices and create instruments
        controller.initializeInstruments(); 

        // Display the most expensive instrument
        controller.displayMostExpensive();  

        // Display sorted instruments by price
        controller.displayInstrumentsSortedByPrice(); 

        // Display the sound for a specific family
        controller.displayFamilySound(); 
    }
}
