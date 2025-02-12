package com.project.megacitycab.dto.customer;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class DeleteCustomerDTO extends IdProps implements SuperDTO {
    public DeleteCustomerDTO() {
    }

    public DeleteCustomerDTO(String id) {
        super(id);
    }
}
