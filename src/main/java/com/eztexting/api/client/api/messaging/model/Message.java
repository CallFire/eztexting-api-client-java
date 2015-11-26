package com.eztexting.api.client.api.messaging.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract EzTexting message
 */
public abstract class Message extends EzTextingModel {
    private String subject;
    private String message;
    private Date stampToSend;
    @JsonProperty("MessageTypeID")
    private MessageType messageType;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStampToSend() {
        return stampToSend;
    }

    public void setStampToSend(Date stampToSend) {
        this.stampToSend = stampToSend;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("phoneNumbers", phoneNumbers)
            .append("groups", groups)
            .append("subject", subject)
            .append("message", message)
            .append("stampToSend", stampToSend)
            .append("messageType", messageType)
            .toString();
    }
}
