package com.project.megacitycab.dto.bill;

import com.project.megacitycab.util.Pagination;

public class GetAllBillsDTO extends Pagination {
    public GetAllBillsDTO() {
    }

    public GetAllBillsDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
