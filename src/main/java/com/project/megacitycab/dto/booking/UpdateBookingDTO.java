package com.project.megacitycab.dto.booking;

import com.project.megacitycab.constant.BookingStatus;
import com.project.megacitycab.dto.SuperDTO;

import java.util.Date;

public class UpdateBookingDTO extends CreateBookingDTO implements SuperDTO {
    private String id;

    public UpdateBookingDTO() {
    }

    public UpdateBookingDTO(String id, String customerId, Date bookingDate, String pickupLocation, String destination, String pickupTime, String driverId, String vehicleId, BookingStatus status, double distance, double fare, double discount, double tax, String userId) {
        super(customerId, bookingDate, pickupLocation, destination, pickupTime, driverId, vehicleId, status, distance, fare, discount, tax, userId);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateBookingDTO{" +
                "id='" + id + '\'' +
                ", customerId='" + getCustomerId() + '\'' +
                ", bookingDate='" + getBookingDate() + '\'' +
                ", pickupLocation='" + getPickupLocation() + '\'' +
                ", destination='" + getDestination() + '\'' +
                ", pickupTime='" + getPickupTime() + '\'' +
                ", driverId='" + getDriverId() + '\'' +
                ", vehicleId='" + getVehicleId() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", distance='" + getDistance() + '\'' +
                ", fare='" + getFare() + '\'' +
                ", discount='" + getDiscount() + '\'' +
                ", tax='" + getTax() + '\'' +
                ", userId='" + getUserId() + '\'' +
                '}';
    }
}
