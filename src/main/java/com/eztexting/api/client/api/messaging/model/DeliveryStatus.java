package com.eztexting.api.client.api.messaging.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Delivery status type
 */
public enum DeliveryStatus {
    NA("na"), DELIVERED("delivered"), NO_CREDITS("no_credits"), BOUNCED("bounced"), OPTED_OUT("opted_out");

    private String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static DeliveryStatus fromValue(String status) {
        for (DeliveryStatus messageType : values()) {
            if (Objects.equals(messageType.status, status)) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("there is no type for DeliveryStatus: " + status);
    }

    @JsonValue
    public String toValue() {
        return status;
    }
}
