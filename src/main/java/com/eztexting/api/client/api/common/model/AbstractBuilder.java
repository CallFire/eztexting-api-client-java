package com.eztexting.api.client.api.common.model;

/**
 * Common builder for request objects
 *
 * @param <R> request type
 */
public class AbstractBuilder<R extends EzTextingModel> {
    protected final R request;

    protected AbstractBuilder(R request) {
        this.request = request;
    }

    /**
     * Build request
     *
     * @return find request pojo
     */
    public R build() {
        return request;
    }
}
