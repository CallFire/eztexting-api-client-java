package com.eztexting.api.client.api.groups.model;

import com.eztexting.api.client.CamelCaseStrategy;
import com.eztexting.api.client.api.common.model.GetRequest;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonNaming(CamelCaseStrategy.class)
public class GetGroupsRequest extends GetRequest {
    private SortProperty sortBy;

    private GetGroupsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request builder
     */
    public static Builder create() {
        return new Builder();
    }

    public SortProperty getSortBy() {
        return sortBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("sortBy", sortBy)
            .toString();
    }

    public enum SortProperty {
        NAME
    }

    /**
     * Builder for get all groups request
     */
    public static class Builder extends GetRequestBuilder<GetGroupsRequest, Builder> {

        private Builder() {
            super(new GetGroupsRequest());
        }

        /**
         * Set property to sort by. Available values: Name
         *
         * @param sortBy property to sort by. Available values: Name
         * @return builder object
         */
        public Builder sortBy(SortProperty sortBy) {
            request.sortBy = sortBy;
            return this;
        }
    }
}
