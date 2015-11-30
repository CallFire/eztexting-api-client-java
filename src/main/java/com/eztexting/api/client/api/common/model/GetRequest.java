package com.eztexting.api.client.api.common.model;

import com.eztexting.api.client.CamelCaseStrategy;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(CamelCaseStrategy.class)
public abstract class GetRequest extends EzTextingModel {
    @JsonProperty("sortDir")
    protected SortType sortType;
    protected Integer itemsPerPage;
    protected Integer page;

    public SortType getSortType() {
        return sortType;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public Integer getPage() {
        return page;
    }

    @SuppressWarnings("unchecked")
    public static class GetRequestBuilder<R extends GetRequest, B extends GetRequestBuilder>
        extends AbstractBuilder<R> {

        protected GetRequestBuilder(R request) {
            super(request);
        }

        /**
         * Set direction of sorting. Available values: asc, desc
         *
         * @param sortType direction of sorting.
         * @return builder object
         */
        public B sortType(SortType sortType) {
            request.sortType = sortType;
            return (B) this;
        }

        /**
         * Set number of results to retrieve. By default, first 10 groups sorted in alphabetical order are retrieved.
         *
         * @param itemsPerPage number of results to retrieve. By default, first 10 groups sorted in alphabetical
         *                     order are retrieved.
         * @return builder object
         */
        public B itemsPerPage(Integer itemsPerPage) {
            request.itemsPerPage = itemsPerPage;
            return (B) this;
        }

        /**
         * Set page of results to retrieve
         *
         * @param page page of results to retrieve
         * @return builder object
         */
        public B page(Integer page) {
            request.page = page;
            return (B) this;
        }
    }
}
