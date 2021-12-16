package org.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;
    private final String FILE_NAME;

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        this.FILE_NAME = fileName;
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

    public void deletePassenger(int id) {
        for (Passenger p : passengerList)
            if (p.getId() == id) {
                passengerList.remove(p);
                break;
            }
    }

    public void save() {
        File file = new File(this.FILE_NAME);
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(file);

            for (Passenger p : this.passengerList) {
                int id = p.getId();
                String name = p.getName();
                String email = p.getEmail();
                String phone = p.getPhone();
                double latitude = p.getLocation().getLatitude();  // Depot GPS location
                double longitude = p.getLocation().getLongitude();

                String info = id+","+name+","+email+","+phone+","+latitude+","+longitude+",";
                fWriter.write(info+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

} // end class
