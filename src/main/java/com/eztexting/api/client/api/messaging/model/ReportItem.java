package com.eztexting.api.client.api.messaging.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Report item from delivery report
 */
public class ReportItem extends EzTextingModel {
    private Long count;
    private String percentage;

    public Long getCount() {
        return count;
    }

    public String getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("count", count)
            .append("percentage", percentage)
            .toString();
    }
}
