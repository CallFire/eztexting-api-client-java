package com.eztexting.api.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * EzTexting API exception is thrown by client in case of 4xx or 5xx HTTP code
 * response
 *
 * @since 1.0
 */
public class EzTextingApiException extends RuntimeException {
    protected List<String> errors;

    public EzTextingApiException(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Get detailed error messages
     *
     * @return detailed messages
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Set detailed error messages
     *
     * @param errors detailed messages
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return errors.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("errors", errors)
            .toString();
    }
}
