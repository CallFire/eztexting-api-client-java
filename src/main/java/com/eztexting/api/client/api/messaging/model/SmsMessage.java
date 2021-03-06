package com.eztexting.api.client.api.messaging.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SMS message to send
 */
public class SmsMessage extends TextMessage {

    public SmsMessage() {
        text.deliveryMethod = DeliveryMethod.EXPRESS;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
