package com.project.megacitycab.dto.booking;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.Pagination;

public class GetAllBookingsDTO extends Pagination implements SuperDTO {
    public GetAllBookingsDTO() {
        super();
    }

    public GetAllBookingsDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
