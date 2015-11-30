package com.eztexting.api.client.api.contacts.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.QueryParamAsNumber;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Contact extends EzTextingModel {
    @JsonProperty("ID")
    private String id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String note;
    private SourceType source;
    @QueryParamAsNumber
    private Boolean optOut;
    private List<String> groups;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date createdAt;

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    public SourceType getSource() {
        return source;
    }

    public Boolean getOptOut() {
        return optOut;
    }

    public List<String> getGroups() {
        return groups;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOptOut(Boolean optOut) {
        this.optOut = optOut;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("phoneNumber", phoneNumber)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("email", email)
            .append("note", note)
            .append("source", source)
            .append("optOut", optOut)
            .append("groups", groups)
            .append("createdAt", createdAt)
            .toString();
    }

    public enum SourceType {
        UNKNOWN("Unknown"), MANUAL("Manually Added"), UPLOAD("Upload"), WEB_WIDGET("Web Widget"), API("API"),
        KEYWORD("Keyword");

        private String source;

        SourceType(String source) {
            this.source = source;
        }

        @JsonCreator
        public static SourceType fromValue(String source) {
            for (SourceType s : values()) {
                if (Objects.equals(s.source, source)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("there is no type for SourceType: " + source);
        }

        @JsonValue
        @Override
        public String toString() {
            return source;
        }
    }
}