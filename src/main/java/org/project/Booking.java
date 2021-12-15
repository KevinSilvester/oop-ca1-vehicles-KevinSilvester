package org.project;

import java.time.LocalDateTime;

class Booking
{
    private int bookingId;
    private int passengerId;
    private int vehicleId;
    private LocalDateTime bookingDateTime;
    private LocationGPS startLocation;
    private LocationGPS endLocation;
    private LocationGPS depotLocation;

    private IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");

    private double cost;  //Calculated at booking time

    public Booking(int passengerId, int vehicleId, LocalDateTime bookingDateTime, LocationGPS startLocation, LocationGPS endLocation, LocationGPS depotLocation, double cost) {
        this.bookingId = idGenerator.getNextId();
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = bookingDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
    }




    public int getPassengerId() { return this.passengerId; }

    public int getBookingId() { return this.bookingId; }

    public int getVehicleId() { return this.vehicleId; }


}
