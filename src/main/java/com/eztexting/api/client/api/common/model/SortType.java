package com.eztexting.api.client.api.common.model;

public enum SortType {
    ASC("asc"), DESC("desc");

    private String type;

    SortType(String property) {
        this.type = property;
    }

    @Override
    public String toString() {
        return type;
    }
}
