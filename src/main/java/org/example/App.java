package org.example;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    private static final Scanner KB = new Scanner(System.in);

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
                String usersInput = KB.nextLine();
                option = Integer.parseInt(usersInput);

                switch (option) {
                    case PASSENGERS:
                        System.out.println("Passengers option chosen");
                        passengerSubmenu();
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

    private void passengerSubmenu() throws IOException {
        final String MENU_ITEMS = "\n*** PASSENGER MENU ***\n"
                + "1. Show all Passengers\n"
                + "2. Find Passenger by Name\n"
                + "3. Add new Passenger\n"
                + "4. Exit\n"
                + "Enter Option [1 - 4]";

        final int SHOW_ALL      = 1;
        final int FIND_BY_NAME  = 2;
        final int ADD           = 3;
        final int EXIT          = 4;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case SHOW_ALL:
                        System.out.println("Display ALL Passengers");
                        passengerStore.displayAllPassengers();
                        break;
                    case FIND_BY_NAME:
                        System.out.println("Find Passenger by Name");
                        System.out.println("Enter passenger name: ");
                        String name = keyboard.nextLine();
                        Passenger p = passengerStore.findPassengerByName(name);
                        if(p==null)
                            System.out.println("No passenger matching the name \"" + name +"\"");
                        else
                            System.out.println("Found passenger: \n" + p.toString());
                        break;
                    case ADD:
                        System.out.println("\nAdd New Passenger");
                        addNewPassenger();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);
    }

    private void addNewPassenger() {
        Pattern phoneRegex = Pattern.compile("^\\+?(\\d+-?)+$");
        Pattern doubleRegex = Pattern.compile("^-?(\\d+)(?:\\.\\d+)?$");
        Pattern emailRegex = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
        String name, email, phone, latitude, longitude;
        boolean valid = true;

        do {
            System.out.print("Enter name: ");
            name = KB.nextLine();
            valid = name.length() > 1;
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter email: ");
            email = KB.nextLine();
            valid = emailRegex.matcher(email).find();
            if (!valid)
                System.out.println("\nInvalid input!\n" + email + name);
        } while (!valid);
        do {
            System.out.print("Enter phone No.: ");
            phone = KB.next();
            valid = phoneRegex.matcher(phone).find();
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter latitude: ");
            latitude = KB.next();
            valid = doubleRegex.matcher(latitude).find() && (Double.parseDouble(latitude) >= -90 && Double.parseDouble(latitude) <= 90);
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter longitude: ");
            longitude = KB.next();
            valid = doubleRegex.matcher(longitude).find() && (Double.parseDouble(longitude) >= -180 && Double.parseDouble(longitude) <= 180);
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);

        passengerStore.addPassenger(name, email, phone, Double.parseDouble(latitude), Double.parseDouble(longitude));
        System.out.println("\nNew Passenger " + name + " added to the list");
    }

    private void close() {
        System.out.println("Program exiting... Goodbye");
    }

}
