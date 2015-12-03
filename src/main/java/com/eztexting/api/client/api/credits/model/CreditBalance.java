package com.eztexting.api.client.api.credits.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CreditBalance extends EzTextingModel {
    private Long planCredits;
    private Long anytimeCredits;
    private Long totalCredits;

    public Long getTotalCredits() {
        return totalCredits;
    }

    public Long getAnytimeCredits() {
        return anytimeCredits;
    }

    public Long getPlanCredits() {
        return planCredits;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("planCredits", planCredits)
            .append("anytimeCredits", anytimeCredits)
            .append("totalCredits", totalCredits)
            .toString();
    }
}
