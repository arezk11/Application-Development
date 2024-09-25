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



package controller;
import model.*;
import view.InstrumentView;
import java.util.Arrays;
import java.util.Scanner;

public class InstrumentController {
    private MusicalInstrument[] instruments;
    private InstrumentView view;

    // Constructor to initialize the view
    public InstrumentController(InstrumentView view) {
        this.view = view;
    }

    // Initialize instruments with the user input
    public void initializeInstruments() {
        instruments = new MusicalInstrument[5];

        // Create the instruments objects
        instruments[0] = new Drum();
        instruments[1] = new Flute();
        instruments[2] = new Guitar();
        instruments[3] = new Harp();
        instruments[4] = new Xylophone();

        // Take input prices from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("--: Requirement 1 :--");
        for (MusicalInstrument instrument : instruments) {
            double price = view.getInstrumentPrice(instrument.toString(), scanner);
            instrument.setPrice(price);
        }
    }

    // Find and display the most expensive instrument
    public void displayMostExpensive() {
        // Sort instruments using compareTo method
        Arrays.sort(instruments); 

        // The most expensive instrument will be the first one in the array
        MusicalInstrument expensive = instruments[0]; 

        System.out.println("\n--: Requirement 2 :--"); 
       // Display the most expensive instrument
        view.displayMostExpensiveInstrument(expensive); 
    }


    // Display the instruments sorted by the price
    public void displayInstrumentsSortedByPrice() {
        Arrays.sort(instruments); 

        System.out.println("\n--: Requirement 3 :--"); 
        // Pass the sorted instruments to the view
        view.displayInstrumentsSortedByPrice(instruments); 
    }

    public void displayFamilySound() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--: Requirement 4 :--");
        System.out.print("Enter an instrument family: ");
        String familyName = scanner.next();

        // Use switch to handle different families and pass the family instruments
        switch (familyName.toLowerCase()) {
            case "string":
                // Pass an array of string instruments to displayFamilyInfo
                displayFamilyInfo(new StringFamily[]{new Guitar(), new Harp()});
                break;

            case "percussion":
                // Pass an array of percussion instruments to displayFamilyInfo
                displayFamilyInfo(new PercussionFamily[]{new Drum(), new Xylophone()});
                break;

            case "woodwind":
                // Pass an array of woodwind instruments to displayFamilyInfo
                displayFamilyInfo(new WoodwindFamily[]{new Flute()});
                break;

            default:
                System.out.println("Invalid family name");
                break;
        }
    }

    // Display instrument sounds for a given family
    public void displayFamilyInfo(MusicalInstrument[] familyInstruments) {
        for (MusicalInstrument instrument : familyInstruments) {
        	// Display the sound of each instrument in the family
            view.displayFamilySound(instrument); 
        }
    }

}
