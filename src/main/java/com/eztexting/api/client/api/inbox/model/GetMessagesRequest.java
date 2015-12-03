package com.eztexting.api.client.api.inbox.model;

import com.eztexting.api.client.CamelCaseStrategy;
import com.eztexting.api.client.api.common.model.GetRequest;
import com.eztexting.api.client.api.inbox.model.InboxMessage.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(CamelCaseStrategy.class)
public class GetMessagesRequest extends GetRequest {
    @JsonProperty("FolderID")
    private Long folderId;
    @JsonProperty("Search")
    private String search;
    @JsonProperty("Filter")
    private MessageType type;
    private SortProperty sortBy;

    private GetMessagesRequest() {
    }

    /**
     * Create request builder
     *
     * @return request builder
     */
    public static Builder create() {
        return new Builder();
    }

    public Long getFolderId() {
        return folderId;
    }

    public String getSearch() {
        return search;
    }

    public MessageType getType() {
        return type;
    }

    public SortProperty getSortBy() {
        return sortBy;
    }

    public enum SortProperty {
        RECEIVED_ON("ReceivedOn"), MESSAGE("Message"), PHONE_NUMBER("PhoneNumber"),;

        private String property;

        SortProperty(String property) {
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
    public static class Builder extends GetRequestBuilder<GetMessagesRequest, Builder> {

        private Builder() {
            super(new GetMessagesRequest());
        }

        /**
         * Configure request to get messages from the selected folder. If FolderID is not given then request will
         * return messages in your Inbox and all folders.
         *
         * @param folderId folder id
         * @return builder object
         */
        public Builder folder(Long folderId) {
            request.folderId = folderId;
            return this;
        }

        /**
         * Configure request to get messages which contain selected text or which are sent from selected phone number.
         *
         * @param search fragment of message or phone number.
         * @return builder object
         */
        public Builder search(String search) {
            request.search = search;
            return this;
        }

        /**
         * Set type of messages to retrieve. Supported values: SMS,MMS
         *
         * @param type type of messages to retrieve
         * @return builder object
         */
        public Builder messageType(MessageType type) {
            request.type = type;
            return this;
        }

        /**
         * Set property to sort by. Available values: ReceivedOn, PhoneNumber, Message
         *
         * @param sortBy property to sort by. Available values: ReceivedOn, PhoneNumber, Message
         * @return builder object
         */
        public Builder sortBy(SortProperty sortBy) {
            request.sortBy = sortBy;
            return this;
        }
    }
}
