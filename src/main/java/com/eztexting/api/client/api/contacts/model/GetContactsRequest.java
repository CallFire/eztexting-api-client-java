package com.eztexting.api.client.api.contacts.model;

import com.eztexting.api.client.CamelCaseStrategy;
import com.eztexting.api.client.api.common.model.GetRequest;
import com.eztexting.api.client.api.contacts.model.Contact.SourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonNaming(CamelCaseStrategy.class)
public class GetContactsRequest extends GetRequest {
    private QueryProperty query;
    private SourceType source;
    private SortProperty sortBy;
    @JsonProperty("optout")
    private Boolean optOut;
    private String group;

    private GetContactsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request builder
     */
    public static Builder create() {
        return new Builder();
    }

    public QueryProperty getQuery() {
        return query;
    }

    public SourceType getSource() {
        return source;
    }

    public SortProperty getSortBy() {
        return sortBy;
    }

    public Boolean getOptOut() {
        return optOut;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("query", query)
            .append("source", source)
            .append("sortBy", sortBy)
            .append("optOut", optOut)
            .append("group", group)
            .toString();
    }

    public enum SortProperty {
        FIRST_NAME("FirstName"), LAST_NAME("LastName"), PHONE_NUMBER("PhoneNumber"), CREATED_AT("CreatedAt");

        private String property;

        SortProperty(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }
    }

    public enum QueryProperty {
        FIRST_NAME("FirstName"), LAST_NAME("LastName"), PHONE_NUMBER("PhoneNumber");

        private String property;

        QueryProperty(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }
    }

    /**
     * Builder for get all groups request
     */
    @SuppressWarnings("unchecked")
    public static class Builder extends GetRequestBuilder<GetContactsRequest, Builder> {

        private Builder() {
            super(new GetContactsRequest());
        }

        /**
         * Search contacts by first name / last name / phone number
         *
         * @param query filter query by one of contact's field
         * @return builder object
         */
        public Builder query(QueryProperty query) {
            request.query = query;
            return this;
        }

        /**
         * Set source of contacts. Available values: 'Unknown', 'Manually Added', 'Upload', 'Web Widget', 'API',
         * 'Keyword'
         *
         * @param source filter by source of contacts
         * @return builder object
         */
        public Builder source(SourceType source) {
            request.source = source;
            return this;
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
         * Set filter by opted out / opted in contacts.
         *
         * @param optOut search only by  opted out contacts
         * @return builder object
         */
        public Builder optOut(Boolean optOut) {
            request.optOut = optOut;
            return this;
        }

        /**
         * Set filter by name of the group the contacts belong to
         *
         * @param group name of the group the contacts belong to
         * @return builder object
         */
        public Builder group(String group) {
            request.group = group;
            return this;
        }
    }
}
