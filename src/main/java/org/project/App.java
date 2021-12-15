package org.project;

import org.javatuples.Pair;
import java.io.IOException;
import java.util.ArrayList;
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
        bookingManager = new BookingManager(passengerStore, vehicleManager);
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
                + "Enter Option [1, 4]";

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
                        vehicleSubmenu();
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
                + "2. Find Passenger by id\n"
                + "3. Find Passenger by Name\n"
                + "4. Add new Passenger\n"
                + "6. Exit\n"
                + "Enter Option [1, 5]";

        final int SHOW_ALL      = 1;
        final int FIND_BY_ID    = 2;
        final int FIND_BY_NAME  = 3;
        final int ADD           = 4;
        final int EDIT          = 5;
        final int EXIT          = 6;

        boolean valid = true;

        int option = 0;
        do {
            Passenger p = null;
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = KB.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case SHOW_ALL:
                        System.out.println("Display ALL Passengers");
                        passengerStore.displayAllPassengers();
                        break;

                    case FIND_BY_ID:
                        System.out.println("Find Passenger ID");
                        String idStr;
                        int id = 0;
                        do {
                            System.out.print("Enter passenger ID: ");
                            idStr = KB.nextLine();
                            try {
                                id = Integer.parseInt(idStr);
                                p = passengerStore.findPassengerById(id);
                                valid = true;
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("\nInvalid input!\n");
                                valid = false;
                            }
                        } while (!valid);

                        if(p==null)
                            System.out.println("No passenger with the id \"" + id +"\"");
                        else
                            System.out.println("Found Passenger: \n" + p);
                        break;

                    case FIND_BY_NAME:
                        System.out.println("Find Passenger by Name");
                        System.out.println("Enter passenger name: ");
                        String name = KB.nextLine();
                        p = passengerStore.findPassengerByName(name);
                        if(p==null)
                            System.out.println("No passenger matching the name \"" + name +"\"");
                        else
                            System.out.println("Found passenger: \n" + p.toString());
                        break;

                    case ADD:
                        System.out.println("Add New Passenger\n");
                        addNewPassenger();
                        break;

                    case EDIT:
                        System.out.println("Edit Passenger\n");
                        editPassenger();
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

    private void editPassenger() {
        final String MENU_ITEMS = "\n*** PASSENGER EDIT MENU ***\n"
                + "1. Edit Name\n"
                + "2. Edit Email\n"
                + "3. Edit Phone\n"
                + "4. Edit Latitude\n"
                + "5. Edit Longitude\n"
                + "6. Exit\n";
        final int EDIT_NAME     = 1;
        final int EDIT_EMAIL    = 2;
        final int EDIT_PHONE    = 3;
        final int EDIT_LATITUDE = 4;
        final int EDIT_LONGITUDE = 5;
        final int EXIT          = 6;

        Pattern phoneRegex = Pattern.compile("^\\+?(\\d+-?)+$");
        Pattern doubleRegex = Pattern.compile("^-?(\\d+)(?:\\.\\d+)?$");
        Pattern emailRegex = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Passenger p = null;
        boolean valid = true;
        String idStr = null;
        int id = 0;

        System.out.println("Saved Passenger IDs: ");
        passengerStore.displayPassengerIDs();
        do {
            System.out.print("Enter passenger ID: ");
            idStr = KB.nextLine();
            try {
                id = Integer.parseInt(idStr);
                p = passengerStore.findPassengerById(id);
                System.out.println(p);
                valid = true;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("\nInvalid input!\n");
                valid = false;
            }
        } while (!valid);

        if(p==null)
            System.out.println("No passenger with the id \"" + id +"\"");
        else {
            int option = 0;
            do {
                System.out.println("\n" + MENU_ITEMS);
                try {
                    option = KB.nextInt();
                    switch (option) {
                        case EDIT_NAME:
                            System.out.println("Edit Passenger Name");
                            do {
                                System.out.print("Enter new name: ");
                                String name = KB.nextLine();
                                valid = name.length() > 1;
                                if (!valid)
                                    System.out.println("\nInvalid input!\n");
                                else {
                                    p.setName(name);
                                    System.out.println("Name Updated");
                                }
                            } while (!valid);
                            break;

                        case EDIT_EMAIL:
                            System.out.println("Edit Passenger Email");
                            do {
                                System.out.print("Enter new email: ");
                                String email = KB.nextLine();
                                valid = emailRegex.matcher(email).find();
                                if (!valid)
                                    System.out.println("\nInvalid input!\n");
                                else {
                                    p.setEmail(email);
                                    System.out.println("Email Updated");
                                }
                            } while (!valid);
                            break;

                        case EDIT_PHONE:
                            System.out.println("Edit Passenger Phone");
                            do {
                                System.out.print("Enter new phone number: ");
                                String phone = KB.nextLine();
                                valid = phoneRegex.matcher(phone).find();
                                if (!valid)
                                    System.out.println("\nInvalid input!\n");
                                else {
                                    p.setPhone(phone);
                                    System.out.println("Phone Updated");
                                }
                            } while (!valid);
                            break;

                        case EDIT_LONGITUDE:
                            System.out.println("Edit Passenger Longitude");
                            System.out.println("Enter new starting Longitude:");
                            do {
                                System.out.print("Enter new longitude [-180, 180]: ");
                                String longitude = KB.nextLine();
                                valid = doubleRegex.matcher(longitude).find() && (Double.parseDouble(longitude) >= -180 && Double.parseDouble(longitude) <= 180);
                                if (!valid)
                                    System.out.println("\nInvalid input!\n");
                                else {
                                    p.setLocation(Double.parseDouble(longitude), p.getLocation().getLatitude());
                                    System.out.println("Longitude Updated");
                                }
                            } while (!valid);
                            break;

                        case EDIT_LATITUDE:
                            System.out.println("Edit Passenger Latitude");
                            do {
                                System.out.print("Enter new latitude [-90, 90]: ");
                                String latitude = KB.nextLine();
                                valid = doubleRegex.matcher(latitude).find() && (Double.parseDouble(latitude) >= -90 && Double.parseDouble(latitude) <= 90);
                                if (!valid)
                                    System.out.println("\nInvalid input!\n");
                                else {
                                    p.setLocation(p.getLocation().getLongitude(), Double.parseDouble(latitude));
                                    System.out.println("Latitude Updated");
                                }
                            } while (!valid);
                            break;

                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.print("Invalid option - please enter number in range");
                }
            }
            while (option != EXIT);
        }
    }

    private void addNewPassenger() {
        Pattern phoneRegex = Pattern.compile("^\\+?(\\d+-?)+$");
        Pattern doubleRegex = Pattern.compile("^-?(\\d+)(?:\\.\\d+)?$");
        Pattern emailRegex = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);
        String name = "", email, phone, latitude, longitude;
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
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter phone number: ");
            phone = KB.nextLine();
            valid = phoneRegex.matcher(phone).find();
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter latitude [-90, 90]: ");
            latitude = KB.nextLine();
            valid = doubleRegex.matcher(latitude).find() && (Double.parseDouble(latitude) >= -90 && Double.parseDouble(latitude) <= 90);
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);
        do {
            System.out.print("Enter longitude [-180, 180]: ");
            longitude = KB.nextLine();
            valid = doubleRegex.matcher(longitude).find() && (Double.parseDouble(longitude) >= -180 && Double.parseDouble(longitude) <= 180);
            if (!valid)
                System.out.println("\nInvalid input!\n");
        } while (!valid);

        System.out.println(passengerStore.addPassenger(name, email, phone, Double.parseDouble(latitude), Double.parseDouble(longitude)));
    }

    private void vehicleSubmenu() {
        final String MENU_ITEMS = "\n*** VEHICLE MENU ***\n"
                + "1. Show all Vehicles\n"
                + "2. Find Vehicles by Registration\n"
                + "3. Search Vehicles\n"
                + "4. Exit\n"
                + "Enter Option [1, 4]";

        final int SHOW_ALL      = 1;
        final int FIND_BY_REGISTRATION  = 2;
        final int SEARCH        = 3;
        final int ADD           = 4;
        final int EXIT          = 5;

        int option = 0;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = KB.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option) {
                    case SHOW_ALL:
                        System.out.println("Display ALL Vehicles");
                        vehicleManager.displayAllVehicles();
                        break;

                    case FIND_BY_REGISTRATION:
                        System.out.println("Find Vehicle by Registration");
                        System.out.println("Enter vehicle registration: ");
                        String registration = KB.nextLine();
                        Vehicle v = vehicleManager.findByRegistration(registration);
                        if(v==null)
                            System.out.println("No vehicle matching the registration \"" + registration +"\"");
                        else
                            System.out.println("Found Vehicle: \n" + v.toString());
                        break;

                    case SEARCH:
                        System.out.println("Search for Vehicle(s)\n");
                        searchVehicles();
                        break;

                    case ADD:
                        System.out.println("Add New Vehicle\n");
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

    private void searchVehicles() {
        final String MENU_ITEMS = "\n*** VEHICLE SEARCH MENU ***\n"
                + "1. Search by Vehicle Type\n"
                + "2. Search by Vehicle Make\n"
                + "3. Search by Vehicle Model\n"
                + "4. Search by Vehicle Seats\n"
                + "5. Exit\n"
                + "Enter Option [1, 5]";

        final int SEARCH_TYPE   = 1;
        final int SEARCH_MAKE   = 2;
        final int SEARCH_MODEL  = 3;
        final int SEARCH_SEATS  = 4;
        final int EXIT          = 5;

        VehicleSearch searchType = null;
        String searchQuery = null;

        int option = 0;
        boolean valid = true;
        do {
            System.out.println("\n" + MENU_ITEMS);
            try {
                String usersInput = KB.nextLine();
                option = Integer.parseInt(usersInput);
                valid = true;
                ArrayList<Vehicle> result;
                switch (option) {
                    case SEARCH_TYPE:
                        System.out.println("Search by Vehicle Type");
                        System.out.print("Enter a vehicle type: ");
                        searchQuery = KB.nextLine();
                        searchType = VehicleSearch.TYPE;
                        break;
                    case SEARCH_MAKE:
                        System.out.println("Search by Vehicle Make");
                        System.out.print("Enter a vehicle make: ");
                        searchQuery = KB.nextLine();
                        searchType = VehicleSearch.MAKE;
                        break;
                    case SEARCH_MODEL:
                        System.out.println("Search by Vehicle Model");
                        System.out.print("Enter a vehicle model: ");
                        searchQuery = KB.nextLine();
                        searchType = VehicleSearch.MODEL;
                        break;
                    case SEARCH_SEATS:
                        System.out.println("Search by Vehicle Seats");
                        System.out.print("Enter a vehicle seats: ");
                        searchQuery = KB.nextLine();
                        searchType = VehicleSearch.SEATS;
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        valid = false;
                        break;
                }

                if (searchQuery != null && searchType != null) {
                    result = vehicleManager.searchVehicleList(searchType, searchQuery);

                    if (result == null) {
                        System.out.println("\nNo vehicles found");
                    }
                    else {
                        System.out.println("\nSearch Results:");
                        for (Vehicle v: result)
                            System.out.println(v);
                    }
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT || !valid);
    }

    private void close() {
        vehicleManager.save();
        System.out.println("Program exiting... Goodbye");
    }
}
