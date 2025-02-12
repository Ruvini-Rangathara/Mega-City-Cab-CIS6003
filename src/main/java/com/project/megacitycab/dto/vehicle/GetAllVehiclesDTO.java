package com.project.megacitycab.dto.vehicle;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.Pagination;

public class GetAllVehiclesDTO extends Pagination implements SuperDTO {

    public GetAllVehiclesDTO() {
        super();
    }

    public GetAllVehiclesDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
