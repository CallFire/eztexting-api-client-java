package com.eztexting.api.client.api.messaging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class TextMessage extends AbstractMessage {
    @JsonUnwrapped
    protected SimpleMessage text = new SimpleMessage();

    @JsonIgnore
    public String getSubject() {
        return text.subject;
    }

    @JsonIgnore
    public void setSubject(String subject) {
        text.subject = subject;
    }

    @JsonIgnore
    public String getMessage() {
        return text.message;
    }

    @JsonIgnore
    public void setMessage(String message) {
        text.message = message;
    }

    @JsonIgnore
    public DeliveryMethod getDeliveryMethod() {
        return text.deliveryMethod;
    }

    @JsonIgnore
    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        text.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("text", text)
            .toString();
    }
}
