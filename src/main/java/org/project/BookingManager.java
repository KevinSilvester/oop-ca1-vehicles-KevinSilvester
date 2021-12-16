package org.project;

import javax.xml.stream.Location;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingManager
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager(PassengerStore passengerStore, VehicleManager vehicleManager) {
        this.bookingList = new ArrayList<>();
        this.passengerStore = passengerStore;
        this.vehicleManager = vehicleManager;
    }

    public void loadBookingsFromFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                

                if (
                        type.equalsIgnoreCase("Van") ||
                                type.equalsIgnoreCase("Truck")
                ) {
                    double loadSpace = sc.nextDouble();
                    vehicleList.add(new Van(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            loadSpace));
                }
                else if (
                        type.equalsIgnoreCase("Car") ||
                                type.equalsIgnoreCase("4x4")
                ) {
                    int seats = sc.nextInt();
                    vehicleList.add(new Car(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            seats));
                }
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    public void addBooking(int passengerId, int vehicleId, LocalDateTime date, LocationGPS start, LocationGPS end) {
        if (this.checkIfAvailable(passengerId, vehicleId)) {
            System.out.println("");
        }
    }

    public boolean checkIfAvailable(int passengerId, int vehicleId) {
        for (Booking b: this.bookingList) {
            if (b.getPassengerId() == passengerId && b.getVehicleId() == vehicleId)
                return false;
        }
        return true;
    }

    private double findCost(int vehicleId, LocationGPS startLocation, LocationGPS endLocation, LocationGPS depotLocation) {
        double distance1 = this.findDistance(depotLocation.getLatitude(), startLocation.getLatitude(), depotLocation.getLongitude(), startLocation.getLongitude());
        double distance2 = this.findDistance(startLocation.getLatitude(), endLocation.getLatitude(), startLocation.getLongitude(), endLocation.getLongitude());
        return (distance1 + distance2)  * 2;
    }

    // source: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
    public static double findDistance(double lat1, double lat2, double lon1, double lon2) {
        final double el1 = 0, el2 = 0;

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
