package com.project.megacitycab.dto.vehicle;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class DeleteVehicleDTO extends IdProps implements SuperDTO {
    public DeleteVehicleDTO() {
    }

    public DeleteVehicleDTO(String id) {
        super(id);
    }
}
