package com.eztexting.api.client;

/**
 * Thrown by client in case of invalid instantiation
 *
 * @since 1.0
 */
public class EzTextingClientException extends RuntimeException {
    public EzTextingClientException() {
    }

    public EzTextingClientException(String message) {
        super(message);
    }

    public EzTextingClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public EzTextingClientException(Throwable cause) {
        super(cause);
    }
}
