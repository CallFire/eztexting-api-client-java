package com.eztexting.api.client.api.messaging.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeliveryReport extends EzTextingModel {
    private ReportItem delivered;
    private ReportItem bounced;
    @JsonProperty("Not Available")
    private ReportItem notAvailable;
    @JsonProperty("Not Sent - No Credits")
    private ReportItem noCredits;
    @JsonProperty("Not Sent - Opted Out")
    private ReportItem optedOut;

    public ReportItem getDelivered() {
        return delivered;
    }

    public ReportItem getBounced() {
        return bounced;
    }

    public ReportItem getNotAvailable() {
        return notAvailable;
    }

    public ReportItem getNoCredits() {
        return noCredits;
    }

    public ReportItem getOptedOut() {
        return optedOut;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("delivered", delivered)
            .append("bounced", bounced)
            .append("notAvailable", notAvailable)
            .append("noCredits", noCredits)
            .append("optedOut", optedOut)
            .toString();
    }
}
