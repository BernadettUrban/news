package com.mjc.school.sortfield;

public enum SortField {
    CREATED("created"),
    MODIFIED("modified");

    private final String databaseFieldName;

    SortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }
}

