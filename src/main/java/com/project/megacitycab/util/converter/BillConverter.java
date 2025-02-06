package com.project.megacitycab.util.converter;

import com.project.megacitycab.dto.bill.BillDTO;
import com.project.megacitycab.model.Bill;

public class BillConverter {

    // Convert Bill (Entity) to BillDTO (DTO)
    public static BillDTO toDTO(Bill bill) {
        if (bill == null) {
            return null;
        }

        BillDTO billDTO = new BillDTO();
        billDTO.setId(bill.getId());
        billDTO.setBookingId(bill.getBookingId());
        billDTO.setFare(bill.getFare());
        billDTO.setTax(bill.getTax());
        billDTO.setDiscount(bill.getDiscount());
        billDTO.setTotalAmount(bill.getTotalAmount());
        billDTO.setStatus(bill.getStatus());
        billDTO.setUserId(bill.getUserId());
        billDTO.setCreatedAt(bill.getCreatedAt());
        billDTO.setUpdatedAt(bill.getUpdatedAt());

        return billDTO;
    }

    // Convert BillDTO (DTO) to Bill (Entity)
    public static Bill toEntity(BillDTO billDTO) {
        if (billDTO == null) {
            return null;
        }

        Bill bill = new Bill();
        bill.setId(billDTO.getId());
        bill.setBookingId(billDTO.getBookingId());
        bill.setFare(billDTO.getFare());
        bill.setTax(billDTO.getTax());
        bill.setDiscount(billDTO.getDiscount());
        bill.setTotalAmount(billDTO.getTotalAmount());
        bill.setStatus(billDTO.getStatus());
        bill.setUserId(billDTO.getUserId());
        bill.setCreatedAt(billDTO.getCreatedAt());
        bill.setUpdatedAt(billDTO.getUpdatedAt());

        return bill;
    }
}
