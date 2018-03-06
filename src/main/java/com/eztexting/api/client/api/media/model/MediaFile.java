package com.eztexting.api.client.api.media.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MediaFile extends EzTextingModel {
    @JsonProperty("ID")
    private Long id;
    private String name;
    private String path;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("id", id)
                .append("name", name)
                .append("path", path)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }
}
