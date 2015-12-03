package com.eztexting.api.client.api.groups.model;

import com.eztexting.api.client.CamelCaseStrategy;
import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.common.model.AbstractBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonNaming(CamelCaseStrategy.class)
public class GetGroupsRequest extends EzTextingModel {
    private SortProperty sortBy;
    @JsonProperty("sortDir")
    private SortType sortType;
    private Integer itemsPerPage;
    private Integer page;

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

    public SortType getSortType() {
        return sortType;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public Integer getPage() {
        return page;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("sortBy", sortBy)
            .append("sortType", sortType)
            .append("itemsPerPage", itemsPerPage)
            .append("page", page)
            .toString();
    }

    public enum SortProperty {
        NAME
    }

    /**
     * Builder for get all groups request
     */
    @SuppressWarnings("unchecked")
    public static class Builder extends AbstractBuilder<GetGroupsRequest> {

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

        /**
         * Set direction of sorting. Available values: asc, desc
         *
         * @param sortType direction of sorting.
         * @return builder object
         */
        public Builder sortType(SortType sortType) {
            request.sortType = sortType;
            return this;
        }

        /**
         * Set number of results to retrieve. By default, first 10 groups sorted in alphabetical order are retrieved.
         *
         * @param itemsPerPage number of results to retrieve. By default, first 10 groups sorted in alphabetical
         *                     order are retrieved.
         * @return builder object
         */
        public Builder itemsPerPage(Integer itemsPerPage) {
            request.itemsPerPage = itemsPerPage;
            return this;
        }

        /**
         * Set page of results to retrieve
         *
         * @param page page of results to retrieve
         * @return builder object
         */
        public Builder page(Integer page) {
            request.page = page;
            return this;
        }
    }
}
