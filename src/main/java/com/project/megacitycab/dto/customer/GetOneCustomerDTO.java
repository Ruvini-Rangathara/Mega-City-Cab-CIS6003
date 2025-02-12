package com.project.megacitycab.dto.customer;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class GetOneCustomerDTO extends IdProps implements SuperDTO {
    public GetOneCustomerDTO() {
    }

    public GetOneCustomerDTO(String id) {
        super(id);
    }
}
