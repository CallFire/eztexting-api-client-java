package com.eztexting.api.client.api.messaging.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Defines message delivery methods.
 * 1 to send via Express delivery method;
 * set to 2 to send via Standard delivery method; set to 3 to send via MMS delivery method
 */
public enum MessageType {
    EXPRESS(1), STANDARD(2), MMS(3);

    private int id;

    MessageType(int id) {
        this.id = id;
    }

    @JsonCreator
    public static MessageType fromValue(String id) {
        for (MessageType messageType : values()) {
            if (Objects.equals(String.valueOf(messageType.id), id)) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("there is no type for MessageTypeID: " + id);
    }

    @JsonValue
    public int toValue() {
        return id;
    }
}
