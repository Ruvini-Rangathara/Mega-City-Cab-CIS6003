package com.project.megacitycab.dto.bill;

import com.project.megacitycab.constant.PaymentStatus;

public class CreateBillDTO {
    private String bookingId;
    private double fare;
    private double tax;
    private double discount;
    private double totalAmount;
    private PaymentStatus status;
    private String userId;

    public CreateBillDTO() {
    }

    public CreateBillDTO(String bookingId, double fare, double tax, double discount, double totalAmount, PaymentStatus status, String userId) {
        this.bookingId = bookingId;
        this.fare = fare;
        this.tax = tax;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CreateBillDTO{" +
                "bookingId='" + bookingId + '\'' +
                ", fare=" + fare +
                ", tax=" + tax +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                '}';
    }
}
