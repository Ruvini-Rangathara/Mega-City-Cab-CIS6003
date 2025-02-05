package com.project.megacitycab.dto.booking;

import com.project.megacitycab.constant.BookingStatus;

import java.util.Date;

public class CreateBookingDTO {
    private String customerId;
    private Date bookingDate;
    private String pickupLocation;
    private String destination;
    private String pickupTime;
    private String driverId;
    private String vehicleId;
    private BookingStatus status;
    private double distance;
    private double fare;
    private double discount;
    private double tax;
    private String userId;

    public CreateBookingDTO() {
    }

    public CreateBookingDTO(String customerId, Date bookingDate, String pickupLocation, String destination, String pickupTime, String driverId, String vehicleId, BookingStatus status, double distance, double fare, double discount, double tax, String userId) {
        this.customerId = customerId;
        this.bookingDate = bookingDate;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.distance = distance;
        this.fare = fare;
        this.discount = discount;
        this.tax = tax;
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CreateBookingDTO{" +
                "customerId='" + customerId + '\'' +
                ", bookingDate=" + bookingDate +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", driverId='" + driverId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", status=" + status +
                ", distance=" + distance +
                ", fare=" + fare +
                ", discount=" + discount +
                ", tax=" + tax +
                ", userId='" + userId + '\'' +
                '}';
    }
}
