package com.project.megacitycab.dto.customer;

import com.project.megacitycab.dto.SuperDTO;
import com.project.megacitycab.util.Pagination;

public class GetAllCustomersDTO extends Pagination implements SuperDTO {
    public GetAllCustomersDTO() {
        super();
    }

    public GetAllCustomersDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
