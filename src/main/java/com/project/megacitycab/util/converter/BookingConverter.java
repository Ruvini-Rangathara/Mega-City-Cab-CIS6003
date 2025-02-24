package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.entity.Booking;
import java.util.List;
import java.util.stream.Collectors;

public class BookingConverter {

    // Convert Booking (Entity) to BookingDTO (DTO)
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }

        return new BookingDTO.BookingDTOBuilder()
                .id(booking.getId())
                .customerId(booking.getCustomerId())
                .bookingDate(booking.getBookingDate())
                .pickupLocation(booking.getPickupLocation())
                .destination(booking.getDestination())
                .pickupTime(booking.getPickupTime())
                .vehicleId(booking.getVehicleId())
                .status(booking.getStatus())
                .distance(booking.getDistance())
                .fare(booking.getFare())
                .discount(booking.getDiscount())
                .tax(booking.getTax())
                .netTotal(booking.getNetTotal())
                .userId(booking.getUserId())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

    // Convert BookingDTO (DTO) to Booking (Entity)
    public static Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Booking.BookingBuilder()
                .id(dto.getId())
                .customerId(dto.getCustomerId())
                .bookingDate(dto.getBookingDate())
                .pickupLocation(dto.getPickupLocation())
                .destination(dto.getDestination())
                .pickupTime(dto.getPickupTime())
                .vehicleId(dto.getVehicleId())
                .status(dto.getStatus())
                .distance(dto.getDistance())
                .fare(dto.getFare())
                .discount(dto.getDiscount())
                .tax(dto.getTax())
                .netTotal(dto.getNetTotal())
                .userId(dto.getUserId())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    // Convert List of Booking entities to List of BookingDTOs
    public static List<BookingDTO> toDTOList(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        }
        return bookings.stream()
                .map(BookingConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Convert List of BookingDTOs to List of Booking entities
    public static List<Booking> toEntityList(List<BookingDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(BookingConverter::toEntity)
                .collect(Collectors.toList());
    }
}
