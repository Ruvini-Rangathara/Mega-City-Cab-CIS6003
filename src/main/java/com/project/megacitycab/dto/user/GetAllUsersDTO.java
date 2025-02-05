package com.project.megacitycab.dto.user;

import com.project.megacitycab.util.Pagination;

public class GetAllUsersDTO extends Pagination {

    public GetAllUsersDTO() {
        super();
    }

    public GetAllUsersDTO(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        super(page, size, sortOrder, searchCriteria);
    }
}
