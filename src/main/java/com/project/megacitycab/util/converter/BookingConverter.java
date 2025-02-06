package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.booking.BookingDTO;
import com.project.megacitycab.model.Booking;

public class BookingConverter {

    // Convert Booking (Entity) to BookingDTO (DTO)
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setPickupLocation(booking.getPickupLocation());
        bookingDTO.setDestination(booking.getDestination());
        bookingDTO.setPickupTime(booking.getPickupTime());
        bookingDTO.setDriverId(booking.getDriverId());
        bookingDTO.setVehicleId(booking.getVehicleId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setDistance(booking.getDistance());
        bookingDTO.setFare(booking.getFare());
        bookingDTO.setDiscount(booking.getDiscount());
        bookingDTO.setTax(booking.getTax());
        bookingDTO.setUserId(booking.getUserId());
        bookingDTO.setCreatedAt(booking.getCreatedAt());
        bookingDTO.setUpdatedAt(booking.getUpdatedAt());

        return bookingDTO;
    }

    // Convert BookingDTO (DTO) to Booking (Entity)
    public static Booking toEntity(BookingDTO bookingDTO) {
        if (bookingDTO == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setCustomerId(bookingDTO.getCustomerId());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setPickupLocation(bookingDTO.getPickupLocation());
        booking.setDestination(bookingDTO.getDestination());
        booking.setPickupTime(bookingDTO.getPickupTime());
        booking.setDriverId(bookingDTO.getDriverId());
        booking.setVehicleId(bookingDTO.getVehicleId());
        booking.setStatus(bookingDTO.getStatus());
        booking.setDistance(bookingDTO.getDistance());
        booking.setFare(bookingDTO.getFare());
        booking.setDiscount(bookingDTO.getDiscount());
        booking.setTax(bookingDTO.getTax());
        booking.setUserId(bookingDTO.getUserId());
        booking.setCreatedAt(bookingDTO.getCreatedAt());
        booking.setUpdatedAt(bookingDTO.getUpdatedAt());

        return booking;
    }
}
