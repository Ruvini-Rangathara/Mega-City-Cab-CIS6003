package com.project.megacitycab.dto.vehicle;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class GetOneVehicleDTO extends IdProps implements SuperDTO {
    public GetOneVehicleDTO() {
    }

    public GetOneVehicleDTO(String id) {
        super(id);
    }
}
