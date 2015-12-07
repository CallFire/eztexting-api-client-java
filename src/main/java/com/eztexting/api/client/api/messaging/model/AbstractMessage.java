package com.eztexting.api.client.api.messaging.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractMessage extends EzTextingModel {
    @JsonProperty("ID")
    private Long id;
    private Long recipientsCount;
    private Long credits;
    private Date stampToSend;
    private List<String> groups = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();
    private List<String> localOptOuts = new ArrayList<>();
    private List<String> globalOptOuts = new ArrayList<>();

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public Date getStampToSend() {
        return stampToSend;
    }

    public void setStampToSend(Date stampToSend) {
        this.stampToSend = stampToSend;
    }

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
            .append("stampToSend", stampToSend)
            .append("groups", groups)
            .append("phoneNumbers", phoneNumbers)
            .append("localOptOuts", localOptOuts)
            .append("globalOptOuts", globalOptOuts)
            .toString();
    }
}
