package com.eztexting.api.client.api.messaging.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Basic message with subject, message itself and type
 */
public class SimpleMessage extends EzTextingModel {
    protected String subject;
    protected String message;
    @JsonProperty("MessageTypeID")
    protected DeliveryMethod deliveryMethod;

    public SimpleMessage() {
    }

    public SimpleMessage( DeliveryMethod deliveryMethod, String subject, String message) {
        this.deliveryMethod = deliveryMethod;
        this.subject = subject;
        this.message = message;
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

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("subject", subject)
            .append("message", message)
            .append("deliveryMethod", deliveryMethod)
            .toString();
    }
}
