package com.eztexting.api.client.api.inbox.model;

import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("New")
    private Boolean unread;
    @JsonProperty("FolderID")
    private Long folderId;
    @JsonProperty("ContactID")
    private String contactId;
    private Date receivedOn;

    public enum MessageType {
        SMS, MMS
    }

    public Long getId() {
        return id;
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

    public Long getFolderId() {
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
