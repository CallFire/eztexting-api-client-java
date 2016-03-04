package com.eztexting.api.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Exception thrown in case if platform returns HTTP code 500 - Internal Server Error
 *
 * @since 1.0
 */
public class InternalServerErrorException extends EzTextingApiException {
    public InternalServerErrorException(List<String> errors) {
        super(500, errors);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
