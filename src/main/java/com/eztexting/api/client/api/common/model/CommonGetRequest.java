package com.eztexting.api.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CommonGetRequest extends GetRequest {

    private CommonGetRequest() {
    }

    /**
     * Create request builder
     *
     * @return request builder
     */
    public static Builder create() {
        return new Builder();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }

    /**
     * Builder for get all groups request
     */
    @SuppressWarnings("unchecked")
    public static class Builder extends GetRequestBuilder<CommonGetRequest, Builder> {
        private Builder() {
            super(new CommonGetRequest());
        }
    }
}
