package com.eztexting.api.client.api.keywords.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CheckAvailabilityResponse extends EzTextingModel {
    private String keyword;
    private Boolean available;

    public CheckAvailabilityResponse() {
    }

    public CheckAvailabilityResponse(String keyword, Boolean available) {
        this.keyword = keyword;
        this.available = available;
    }

    public String getKeyword() {
        return keyword;
    }

    public Boolean getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("keyword", keyword)
            .append("available", available)
            .toString();
    }
}
