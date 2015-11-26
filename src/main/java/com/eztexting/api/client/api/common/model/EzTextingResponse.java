package com.eztexting.api.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Base response object from EzTexting service
 *
 * @param <T> type of response entity
 */

public class EzTextingResponse<T> extends EzTextingModel {

    @JsonProperty("Response")
    private ResponseHolder<T> holder;

    public EzTextingResponse() {
    }

    public EzTextingResponse(String status, int code, T entry) {
        this.holder = new ResponseHolder<>(status, code, entry);
    }

    public EzTextingResponse(String status, int code, List<T> entries) {
        this.holder = new ResponseHolder<>(status, code, entries);
    }

    @JsonIgnore
    public String getStatus() {
        return holder.status;
    }

    @JsonIgnore
    public Integer getCode() {
        return holder.code;
    }

    @JsonIgnore
    public T getEntry() {
        return holder.entry;
    }

    @JsonIgnore
    public List<T> getEntries() {
        return holder.entries;
    }

    @JsonIgnore
    public List<String> getErrors() {
        return holder.errors;
    }

    @JsonIgnore
    public InputStream getContent() {
        return holder.content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("response", holder)
            .toString();
    }

    public static EzTextingResponse<InputStream> withContent(InputStream is) {
        EzTextingResponse<InputStream> response = new EzTextingResponse<>();
        response.holder.content = is;
        return response;
    }

    static class ResponseHolder<T> {
        private String status;
        private Integer code;
        private T entry;
        private List<T> entries;
        private List<String> errors = new ArrayList<>();
        private InputStream content;

        public ResponseHolder() {
        }

        public ResponseHolder(String status, Integer code, T entry) {
            this.status = status;
            this.code = code;
            this.entry = entry;
        }

        public ResponseHolder(String status, Integer code, List<T> entries) {
            this.status = status;
            this.code = code;
            this.entries = entries;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("status", status)
                .append("code", code)
                .append("entry", entry)
                .append("entries", entries)
                .append("errors", errors)
                .toString();
        }
    }
}
