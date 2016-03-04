package com.eztexting.api.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Exception thrown in case if platform returns HTTP code 404 - NOT FOUND, the resource requested does not exist
 *
 * @since 1.0
 */
public class ResourceNotFoundException extends EzTextingApiException {
    public ResourceNotFoundException(List<String> errors) {
        super(404, errors);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
