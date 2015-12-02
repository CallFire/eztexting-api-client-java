package com.eztexting.api.client.api.keywords.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.QueryParamIgnore;
import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Keyword extends EzTextingModel {
    @QueryParamIgnore
    @JsonProperty("ID")
    private Long id;
    private String keyword;
    private Boolean enableDoubleOptIn;
    private Boolean enableAlternateReply;
    private SimpleMessage confirmMessage;
    private SimpleMessage joinMessage;
    private SimpleMessage alternateReply;
    private String forwardEmail;
    private String forwardUrl;
    @JsonProperty("ContactGroupIDs")
    private List<String> contactGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getEnableDoubleOptIn() {
        return enableDoubleOptIn;
    }

    public void setEnableDoubleOptIn(Boolean enableDoubleOptIn) {
        this.enableDoubleOptIn = enableDoubleOptIn;
    }

    public Boolean getEnableAlternateReply() {
        return enableAlternateReply;
    }

    public void setEnableAlternateReply(Boolean enableAlternateReply) {
        this.enableAlternateReply = enableAlternateReply;
    }

    public SimpleMessage getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(SimpleMessage confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public SimpleMessage getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(SimpleMessage joinMessage) {
        this.joinMessage = joinMessage;
    }

    public SimpleMessage getAlternateReply() {
        return alternateReply;
    }

    public void setAlternateReply(SimpleMessage alternateReply) {
        this.alternateReply = alternateReply;
    }

    public String getForwardEmail() {
        return forwardEmail;
    }

    public void setForwardEmail(String forwardEmail) {
        this.forwardEmail = forwardEmail;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public List<String> getContactGroups() {
        return contactGroups;
    }

    public void setContactGroups(List<String> contactGroups) {
        this.contactGroups = contactGroups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("keyword", keyword)
            .append("enableDoubleOptIn", enableDoubleOptIn)
            .append("enableAlternateReply", enableAlternateReply)
            .append("confirmMessage", confirmMessage)
            .append("joinMessage", joinMessage)
            .append("alternateReply", alternateReply)
            .append("forwardEmail", forwardEmail)
            .append("forwardUrl", forwardUrl)
            .append("contactGroups", contactGroups)
            .toString();
    }
}
