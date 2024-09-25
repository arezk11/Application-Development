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

package view;

import model.MusicalInstrument;
import java.util.Scanner;

public class InstrumentView {

    // Take the user input for the price of each instrument
    public double getInstrumentPrice(String instrumentName, Scanner scanner) {
        System.out.print("Enter the price for " + instrumentName + ": ");
        double price = scanner.nextDouble();
        return price;
    }

    // Display the most expensive instrument
    public void displayMostExpensiveInstrument(MusicalInstrument instrument) {
        System.out.println("The most expensive instrument is: " + instrument);
        System.out.println(instrument + "’s cost is: $" + String.format("%.2f", instrument.getPrice()));
        System.out.println(instrument + " is played: " + instrument.howToPlay());
        System.out.println(instrument + " fixing: " + instrument.howToFix());
        System.out.println(instrument + " pitch type: " + instrument.getPitchType());
    }

    // Display the list of instruments in descending order by price
    public void displayInstrumentsSortedByPrice(MusicalInstrument[] instruments) {
        System.out.println("Instruments in price descending order: "); 
        System.out.print("[");
        for (int i = 0; i < instruments.length; i++) {
            System.out.print(instruments[i]);
            if (i < instruments.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]"); 
    }

    // Display how the instrument family make the sound
    public void displayFamilySound(MusicalInstrument instrument) {
        System.out.println(instrument + " makes sound " + instrument.makeSound() + ".");
    }
}
