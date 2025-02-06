package com.project.megacitycab.util.exception;

public enum MegaCityCabExceptionType {

    // User Exceptions
    USER_NOT_FOUND(60101, "User not found"),
    USER_ALREADY_EXISTS(60102, "User already exists"),
    INVALID_USER_CREDENTIALS(60103, "Invalid user credentials"),
    USER_NOT_ACTIVE(60104, "User is not active"),
    INVALID_USER_ROLE(60105, "User role is not authorized"),

    // Driver Exceptions
    DRIVER_NOT_FOUND(60201, "Driver not found"),
    DRIVER_ALREADY_EXISTS(60202, "Driver already exists"),
    DRIVER_LICENSE_INVALID(60203, "Driver license is not valid"),
    DRIVER_NOT_AVAILABLE(60204, "Driver is not available for assignment"),
    DRIVER_SUSPENDED(60205, "Driver is suspended"),

    // Customer Exceptions
    CUSTOMER_NOT_FOUND(60301, "Customer not found"),
    CUSTOMER_ALREADY_EXISTS(60302, "Customer already exists"),
    INVALID_CUSTOMER_DETAILS(60303, "Invalid customer details"),
    CUSTOMER_BLOCKED(60304, "Customer is blocked from booking rides"),

    // Vehicle Exceptions
    VEHICLE_NOT_FOUND(60401, "Vehicle not found"),
    VEHICLE_ALREADY_REGISTERED(60402, "Vehicle is already registered"),
    VEHICLE_NOT_AVAILABLE(60403, "Vehicle is not available for booking"),
    VEHICLE_MAINTENANCE_REQUIRED(60404, "Vehicle requires maintenance"),
    VEHICLE_TYPE_NOT_SUPPORTED(60405, "Vehicle type is not supported"),

    // Booking Exceptions
    BOOKING_NOT_FOUND(60501, "Booking not found"),
    BOOKING_ALREADY_CANCELLED(60502, "Booking is already cancelled"),
    BOOKING_PAYMENT_FAILED(60503, "Payment failed for the booking"),
    BOOKING_NOT_ALLOWED(60504, "Booking is not allowed for this user"),
    BOOKING_TIME_CONFLICT(60505, "Booking time conflicts with another booking"),

    // Bill Exceptions
    BILL_NOT_FOUND(60601, "Bill not found"),
    BILL_ALREADY_PAID(60602, "Bill has already been paid"),
    BILL_GENERATION_FAILED(60603, "Bill generation failed"),
    INVALID_BILL_DETAILS(60604, "Invalid bill details"),

    // General System Exceptions
    INTERNAL_SERVER_ERROR(69999, "An unexpected error occurred");

    private final int code;
    private final String message;

    MegaCityCabExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
