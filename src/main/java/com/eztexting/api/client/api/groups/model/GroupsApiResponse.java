package com.eztexting.api.client.api.groups.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Response sent from EzTexting platform on group creation request
 */
public class GroupsApiResponse extends Group {
    private Long contactCount;

    public Long getContactCount() {
        return contactCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("contactCount", contactCount)
            .toString();
    }
}
