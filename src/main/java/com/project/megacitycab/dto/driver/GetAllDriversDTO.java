package com.project.megacitycab.dto.driver;

import com.project.megacitycab.util.Pagination;

public class GetAllDriversDTO extends Pagination {
    public GetAllDriversDTO() {
        super();
    }

    public GetAllDriversDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
