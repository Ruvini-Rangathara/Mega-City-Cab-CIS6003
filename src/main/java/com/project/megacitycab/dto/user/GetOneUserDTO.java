package com.project.megacitycab.dto.user;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class GetOneUserDTO extends IdProps implements SuperDTO {
    public GetOneUserDTO() {
    }

    public GetOneUserDTO(String id) {
        super(id);
    }
}
