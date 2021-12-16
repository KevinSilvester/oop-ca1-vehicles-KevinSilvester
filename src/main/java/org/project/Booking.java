package org.project;

import java.time.LocalDateTime;
import java.util.Objects;

class Booking
{
    private int bookingId;
    private int passengerId;
    private int vehicleId;
    private LocalDateTime bookingDateTime;
    private LocationGPS startLocation;
    private LocationGPS endLocation;

    private IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");

    private double cost;  //Calculated at booking time

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public void setStartLocation(LocationGPS startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(LocationGPS endLocation) {
        this.endLocation = endLocation;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public double getCost() {
        return cost;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    public LocationGPS getEndLocation() {
        return endLocation;
    }

    public LocationGPS getStartLocation() {
        return startLocation;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return getPassengerId() == booking.getPassengerId() && getVehicleId() == booking.getVehicleId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassengerId(), getVehicleId());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", vehicleId=" + vehicleId +
                ", bookingDateTime=" + bookingDateTime +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", idGenerator=" + idGenerator +
                ", cost=" + cost +
                '}';
    }




}
