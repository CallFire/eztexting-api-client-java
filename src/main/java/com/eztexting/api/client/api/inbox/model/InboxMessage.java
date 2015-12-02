package com.eztexting.api.client.api.inbox.model;

import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

/**
 * Message stored in your inbox folder
 */
public class InboxMessage extends SimpleMessage {
    @JsonProperty("ID")
    private Long id;
    private String phoneNumber;
    private MessageType type;
    private List<String> files;
    private Boolean unread;
    private String folderId;
    private String contactId;
    private Date receivedOn;

    public enum MessageType {
        SMS, MMS
    }

    public Long getId() {
        return id;
    }

    @JsonSetter("New")
    private void setUnread(Integer unread) {
        this.unread = BooleanUtils.toBooleanObject(unread);
    }

    @JsonGetter("New")
    private Integer getUnread() {
        return BooleanUtils.toIntegerObject(unread);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MessageType getType() {
        return type;
    }

    public List<String> getFiles() {
        return files;
    }

    public Boolean isUnread() {
        return unread;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getContactId() {
        return contactId;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("phoneNumber", phoneNumber)
            .append("type", type)
            .append("files", files)
            .append("unread", unread)
            .append("folderId", folderId)
            .append("contactId", contactId)
            .append("receivedOn", receivedOn)
            .toString();
    }
}
