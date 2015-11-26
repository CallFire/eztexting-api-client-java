package com.eztexting.api.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Exception thrown in case if platform returns HTTP code 401 - Unauthorized, API Key missing or invalid
 *
 * @since 1.0
 */
public class UnauthorizedException extends EzTextingApiException {
    public UnauthorizedException(List<String> errors) {
        super(errors);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
