package com.eztexting.api.client.api.messaging.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * EzTexting server response for send API operation
 */
public class SendMessageResponse extends MmsMessage {
    @JsonProperty("ID")
    private Long id;
    private Long recipientsCount;
    private Long credits;
    private List<String> localOptOuts = new ArrayList<>();
    private List<String> globalOptOuts = new ArrayList<>();

    /**
     * Get unique ID referencing the message
     *
     * @return unique ID referencing the message
     */
    public Long getId() {
        return id;
    }

    /**
     * Get number of intended recipients. Please note: This includes globally opted out numbers.
     *
     * @return number of intended recipients. Please note: This includes globally opted out numbers.
     */
    public Long getRecipientsCount() {
        return recipientsCount;
    }

    /**
     * Get number of credits charged for the message
     *
     * @return number of credits charged for the message
     */
    public Long getCredits() {
        return credits;
    }

    /**
     * Get array of locally opted-out phone numbers
     *
     * @return array of locally opted-out phone numbers
     */
    public List<String> getLocalOptOuts() {
        return localOptOuts;
    }

    /**
     * Get array of globally opted-out phone numbers
     *
     * @return array of globally opted-out phone numbers
     */
    public List<String> getGlobalOptOuts() {
        return globalOptOuts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("recipientsCount", recipientsCount)
            .append("credits", credits)
            .append("localOptOuts", localOptOuts)
            .append("globalOptOuts", globalOptOuts)
            .toString();
    }
}
