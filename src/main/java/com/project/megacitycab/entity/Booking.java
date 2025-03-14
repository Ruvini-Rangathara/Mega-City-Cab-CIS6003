package com.project.megacitycab.entity;

import com.project.megacitycab.constant.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Booking implements SuperEntity {
    private final String id;
    private final String customerId;
    private final LocalDate bookingDate;
    private final String pickupLocation;
    private final String destination;
    private final LocalTime pickupTime;
    private final LocalTime releaseTime;
    private final String vehicleId;
    private final BookingStatus status;
    private final double distance;
    private final double fare;
    private final double discount;
    private final double tax;
    private final double netTotal;
    private final String userId;
    private final Date createdAt;
    private final Date updatedAt;

    private Booking(BookingBuilder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.bookingDate = builder.bookingDate;
        this.pickupLocation = builder.pickupLocation;
        this.destination = builder.destination;
        this.pickupTime = builder.pickupTime;
        this.releaseTime = builder.releaseTime;
        this.vehicleId = builder.vehicleId;
        this.status = builder.status;
        this.distance = builder.distance;
        this.fare = builder.fare;
        this.discount = builder.discount;
        this.tax = builder.tax;
        this.netTotal = builder.netTotal;
        this.userId = builder.userId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Getters only since we're using immutable pattern
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public LocalTime getReleaseTime() {
        return releaseTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTax() {
        return tax;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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
                ", releaseTime='" + releaseTime + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", status=" + status +
                ", distance=" + distance +
                ", fare=" + fare +
                ", discount=" + discount +
                ", tax=" + tax +
                ", netTotal=" + netTotal +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // Static Builder class
    public static class BookingBuilder {
        private String id;
        private String customerId;
        private LocalDate bookingDate;
        private String pickupLocation;
        private String destination;
        private LocalTime pickupTime;
        private LocalTime releaseTime;
        private String vehicleId;
        private BookingStatus status;
        private double distance;
        private double fare;
        private double discount;
        private double tax;
        private double netTotal;
        private String userId;
        private Date createdAt;
        private Date updatedAt;

        public BookingBuilder() {
            // Set default values if needed
            this.discount = 0.0;
            this.tax = 0.0;
            this.status = BookingStatus.pending;
        }

        public BookingBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BookingBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public BookingBuilder bookingDate(LocalDate bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public BookingBuilder pickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public BookingBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public BookingBuilder pickupTime(LocalTime pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }

        public BookingBuilder releaseTime(LocalTime releaseTime) {
            this.releaseTime = releaseTime;
            return this;
        }

        public BookingBuilder vehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public BookingBuilder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public BookingBuilder distance(double distance) {
            this.distance = distance;
            return this;
        }

        public BookingBuilder fare(double fare) {
            this.fare = fare;
            return this;
        }

        public BookingBuilder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public BookingBuilder tax(double tax) {
            this.tax = tax;
            return this;
        }

        public BookingBuilder netTotal(double netTotal) {
            this.netTotal = netTotal;
            return this;
        }

        public BookingBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public BookingBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookingBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
