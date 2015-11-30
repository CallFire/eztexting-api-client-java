package com.eztexting.api.client.api.messaging.model;

/**
 * Delivery status type
 */
public enum DeliveryStatus {
    NA("na"), DELIVERED("delivered"), NO_CREDITS("no_credits"), BOUNCED("bounced"), OPTED_OUT("opted_out");

    private String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
