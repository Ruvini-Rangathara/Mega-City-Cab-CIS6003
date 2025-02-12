package com.project.megacitycab.dto.driver;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.IdProps;

public class GetOneDriverDTO extends IdProps implements SuperDTO {
    public GetOneDriverDTO() {
    }

    public GetOneDriverDTO(String id) {
        super(id);
    }
}
