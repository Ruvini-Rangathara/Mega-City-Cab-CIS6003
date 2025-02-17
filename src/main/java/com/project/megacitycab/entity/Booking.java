package com.project.megacitycab.entity;

import com.project.megacitycab.constant.BookingStatus;

import java.util.Date;

public class Booking implements SuperEntity {
    private String id;
    private String customerId;
    private Date bookingDate;
    private String pickupLocation;
    private String destination;
    private String pickupTime;
    private String vehicleId;
    private BookingStatus status;
    private double distance;
    private double fare;
    private double discount;
    private double tax;
    private String userId;
    private Date createdAt;
    private Date updatedAt;

    public Booking() {
    }

    public Booking(String id, String customerId, Date bookingDate, String pickupLocation, String destination, String pickupTime, String vehicleId, BookingStatus status, double distance, double fare, double discount, double tax, String userId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.bookingDate = bookingDate;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.vehicleId = vehicleId;
        this.status = status;
        this.distance = distance;
        this.fare = fare;
        this.discount = discount;
        this.tax = tax;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", bookingDate=" + bookingDate +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", status=" + status +
                ", distance=" + distance +
                ", fare=" + fare +
                ", discount=" + discount +
                ", tax=" + tax +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
