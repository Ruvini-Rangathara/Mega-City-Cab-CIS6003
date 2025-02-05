package com.project.megacitycab.dto.bill;

import com.project.megacitycab.constant.PaymentStatus;

import java.util.Date;

public class BillDTO {
    private String id;
    private String bookingId;
    private double fare;
    private double tax;
    private double discount;
    private double totalAmount;
    private PaymentStatus status;
    private String userId;
    private Date createdAt;
    private Date updatedAt;

    public BillDTO() {
    }

    public BillDTO(String id, String bookingId, double fare, double tax, double discount, double totalAmount, PaymentStatus status, String userId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.fare = fare;
        this.tax = tax;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.status = status;
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
        return "Bill{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", fare=" + fare +
                ", tax=" + tax +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
