package com.eztexting.api.client.api.messaging.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract EzTexting message
 */
public abstract class AbstractMessage extends SimpleMessage {
    private Date stampToSend;
    private List<String> phoneNumbers = new ArrayList<>();
    private List<String> groups = new ArrayList<>();

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("phoneNumbers", phoneNumbers)
            .append("groups", groups)
            .append("stampToSend", stampToSend)
            .toString();
    }
}
