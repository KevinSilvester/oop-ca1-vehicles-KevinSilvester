package org.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    private void loadPassengerDataFromFile(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.useDelimiter("[,\r\n]+");
            while (sc.hasNext()) {
                int id = sc.nextInt();
                String name = sc.next();
                String email = sc.next();
                String phone = sc.next();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();
                passengerList.add(new Passenger(id, name, email, phone, latitude, longitude));
            }
            sc.close();
        }
        catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    public List<Passenger> getAllPassengers() {
        return this.passengerList;
    }

    public void displayAllPassengers() {
        for (Passenger p : this.passengerList) {
            System.out.println(p.toString());
        }
    }

    public void displayPassengerIDs() {
        for (Passenger p : this.passengerList) {
            System.out.println(p.getId());
        }
    }

    public String addPassenger(String name, String email, String phone,
                             double latitude, double longitude) {
        for (Passenger p : this.passengerList) {
            if (p.getName().toLowerCase().equals(name.toLowerCase()) &&
                    p.getEmail().toLowerCase().equals(email.toLowerCase()))
                return "\nPassenger " + name + " with email " + " is already stored";
        }
        Passenger newPassenger = new Passenger(name, email, phone, latitude, longitude);
        this.passengerList.add(newPassenger);
        return "\nPassenger \"" + name + "\" with email \"" + email + "\" has been added";
    }

    public Passenger findPassengerByName(String query) {
        for (Passenger p : this.passengerList) {
            if (p.getName().toLowerCase().equals(query.trim().toLowerCase()))
                return p;
        }
        return null;
    }

    public Passenger findPassengerById(int id) {
        for (Passenger p : passengerList)
            if (p.getId() == id) {
                return p;
            }
        return null;
    }

    // TODO - see functional spec for details of code to add

} // end class
