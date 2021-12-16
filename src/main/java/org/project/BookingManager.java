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

    public void displayAllBookings() {
        for (Booking b : this.bookingList)
            System.out.println(b);
    }

    public void addBooking(int passengerId, int vehicleId, LocalDateTime date, LocationGPS start, LocationGPS end) {
        if (this.checkIfAvailable(passengerId, vehicleId, date)) {
            this.bookingList.add(new Booking(passengerId, vehicleId, date, start, end));
            System.out.println("Booking added successfully");
        }
        else {
            System.out.println("Booking was unsuccessful.\nTry booking for in a different time slot.");
        }
    }

    public boolean checkIfAvailable(int passengerId, int vehicleId, LocalDateTime date) {
        for (Booking b: this.bookingList) {
            if (b.getPassengerId() == passengerId && b.getVehicleId() == vehicleId && !b.getBookingDateTime().isAfter(date))
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
