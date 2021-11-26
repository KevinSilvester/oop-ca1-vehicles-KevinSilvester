package org.example;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This Vehicle Bookings Management Systems manages the booking of Vehicles
 * by Passengers.
 *
 * This program reads from 3 text files:
 * "vehicles.txt", "passengers.txt", and "next-id-store.txt"
 * You should be able to see them in the project pane.
 * You will create "bookings.txt" at a later stage, to store booking records.
 *
 * "next-id-store.txt" contains one number ("201"), which will be the
 * next auto-generated id to be used to when new vehicles, passengers, or
 * bookings are created.  The value in the file will be updated when new objects
 * are created - but not when objects are recreated from records in
 * the files - as they already have IDs.  Dont change it - it will be updated by
 * the IdGenerator class.
 */

public class App {
    private static PassengerStore passengerStore;
    private static VehicleManager vehicleManager;
    private static BookingManager bookingManager;

    public static void main(String[] args) {
        App app = new App();
        app.start();

        app.close();
    }

    private void start() {
        passengerStore = new PassengerStore("passengers.txt");
        vehicleManager = new VehicleManager("vehicles.txt");
        System.out.println("\nWelcome to the VEHICLE BOOKINGS MANAGEMENT SYSTEM - CA1 for OOP\n");
        try {
            displayMainMenu();        // User Interface - Menu
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayMainMenu() throws IOException {
        final Scanner keyboard = new Scanner(System.in);
        final String MENU_ITEMS = "\n*** MAIN MENU OF OPTIONS ***\n"
                + "1. Passengers\n"
                + "2. Vehicles\n"
                + "3. Bookings\n"
                + "4. Exit\n"
                + "Enter Option [1 - 4]";

        final int PASSENGERS  = 1;
        final int VEHICLES    = 2;
        final int BOOKINGS    = 3;
        final int EXIT        = 4;

        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case PASSENGERS:
                        System.out.println("Passengers option chosen");
//                        displayPassengerMenu();
                        break;
                    case VEHICLES:
                        System.out.println("Vehicles option chosen");
                        break;
                    case BOOKINGS:
                        System.out.println("Bookings option chosen");
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }
            }
            catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

        System.out.println("\nExiting Main Menu, goodbye.");
    }

    private void close() {
        System.out.println("Program exiting... Goodbye");
    }

}
