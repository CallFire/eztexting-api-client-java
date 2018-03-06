package com.eztexting.api.client.api.media.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MediaFile extends EzTextingModel {
    @JsonProperty("ID")
    private Long id;
    private String name;
    private String path;
}
