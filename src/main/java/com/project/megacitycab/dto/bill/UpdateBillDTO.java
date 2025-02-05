package com.project.megacitycab.dto.bill;

import com.project.megacitycab.constant.PaymentStatus;

public class UpdateBillDTO extends CreateBillDTO {
    private String id;

    public UpdateBillDTO() {
    }

    public UpdateBillDTO(String id, String bookingId, double fare, double tax, double discount, double totalAmount, PaymentStatus status, String userId) {
        super(bookingId, fare, tax, discount, totalAmount, status, userId);
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
        return "UpdateBillDTO{" +
                "id='" + id + '\'' +
                ", bookingId='" + getBookingId() + '\'' +
                ", fare='" + getFare() + '\'' +
                ", tax='" + getTax() + '\'' +
                ", discount='" + getDiscount() + '\'' +
                ", totalAmount='" + getTotalAmount() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", userId='" + getUserId() + '\'' +
                '}';
    }
}
