package com.eztexting.api.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Exception thrown in case if platform returns HTTP code 400 - Bad request, the request was formatted improperly.
 *
 * @since 1.0
 */
public class BadRequestException extends EzTextingApiException {
    public BadRequestException(List<String> errors) {
        super(errors);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
