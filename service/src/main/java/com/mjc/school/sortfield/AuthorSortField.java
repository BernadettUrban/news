package com.mjc.school.sortfield;

import org.springframework.data.domain.Sort;

public enum AuthorSortField {
    NEWS_COUNT_DESC("newsCount", Sort.Direction.DESC);

    private final String field;
    private final Sort.Direction direction;

    AuthorSortField(String newsCount, Sort.Direction desc) {
        this.field = newsCount;

        this.direction = desc;
    }

    public String getField() {
        return field;
    }

    public Sort.Direction getDirection() {
        return direction;
    }
}
