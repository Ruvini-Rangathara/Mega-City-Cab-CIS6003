package com.project.megacitycab.util;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
    private int page;
    private int size;          // Number of records per page
    private SortOrder sortOrder;
    private SearchCriteria searchCriteria;

    public enum SortOrder {
        ASC, DESC
    }

    public static class SearchCriteria {
        private Map<String, String> filters;

        public SearchCriteria() {
            this.filters = new HashMap<>();
        }

        public SearchCriteria(Map<String, String> filters) {
            this.filters = filters != null ? filters : new HashMap<>();
        }

        public Map<String, String> getFilters() {
            return filters;
        }

        public void setFilters(Map<String, String> filters) {
            this.filters = filters != null ? filters : new HashMap<>();
        }

        public void addFilter(String column, String value) {
            if (column != null && value != null) {
                this.filters.put(column, value);
            }
        }

        public void removeFilter(String column) {
            if (column != null) {
                this.filters.remove(column);
            }
        }

        @Override
        public String toString() {
            return "SearchCriteria{" + "filters=" + filters + '}';
        }
    }

    public Pagination() {
        this.page = 1;
        this.size = 10;
        this.sortOrder = SortOrder.ASC;
        this.searchCriteria = new SearchCriteria();
    }

    public Pagination(int page, int size, SortOrder sortOrder, SearchCriteria searchCriteria) {
        this.page = page > 0 ? page : 1;
        this.size = size > 0 ? size : 10;
        this.sortOrder = sortOrder != null ? sortOrder : SortOrder.ASC;
        this.searchCriteria = searchCriteria != null ? searchCriteria : new SearchCriteria();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page > 0 ? page : 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : 10;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder != null ? sortOrder : SortOrder.ASC;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria != null ? searchCriteria : new SearchCriteria();
    }

    // Calculate the offset for SQL queries
    public int getOffset() {
        return (page - 1) * size;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", size=" + size +
                ", sortOrder=" + sortOrder +
                ", searchCriteria=" + searchCriteria +
                '}';
    }
}
