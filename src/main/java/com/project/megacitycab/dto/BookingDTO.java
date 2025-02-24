package com.project.megacitycab.dto;

import com.project.megacitycab.constant.BookingStatus;
import java.util.Date;

public class BookingDTO implements SuperDTO {
    private final String id;
    private final String customerId;
    private final Date bookingDate;
    private final String pickupLocation;
    private final String destination;
    private final String pickupTime;
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

    private BookingDTO(BookingDTOBuilder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.bookingDate = builder.bookingDate;
        this.pickupLocation = builder.pickupLocation;
        this.destination = builder.destination;
        this.pickupTime = builder.pickupTime;
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

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getPickupTime() {
        return pickupTime;
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
        return "BookingDTO{" +
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
                ", netTotal=" + netTotal +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // Static Builder class
    public static class BookingDTOBuilder {
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
        private double netTotal;
        private String userId;
        private Date createdAt;
        private Date updatedAt;

        public BookingDTOBuilder() {
            // Initialize default values
            this.discount = 0.0;
            this.tax = 0.0;
            this.status = BookingStatus.pending;
            this.bookingDate = new Date();
        }

        public BookingDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BookingDTOBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public BookingDTOBuilder bookingDate(Date bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public BookingDTOBuilder pickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public BookingDTOBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public BookingDTOBuilder pickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }

        public BookingDTOBuilder vehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public BookingDTOBuilder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public BookingDTOBuilder distance(double distance) {
            this.distance = distance;
            return this;
        }

        public BookingDTOBuilder fare(double fare) {
            this.fare = fare;
            return this;
        }

        public BookingDTOBuilder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public BookingDTOBuilder tax(double tax) {
            this.tax = tax;
            return this;
        }

        public BookingDTOBuilder netTotal(double netTotal) {
            this.netTotal = netTotal;
            return this;
        }

        public BookingDTOBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public BookingDTOBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookingDTOBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BookingDTO build() {
            return new BookingDTO(this);
        }
    }
}
