package com.project.megacitycab.dto.booking;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class GetOneBookingDTO extends IdProps implements SuperDTO {

    public GetOneBookingDTO() {
    }

    public GetOneBookingDTO(String id) {
        super(id);
    }
}
