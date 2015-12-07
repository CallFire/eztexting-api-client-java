package com.eztexting.api.client.api.common.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Converts annotated field to number if possible, e.g. true: 1, false: 0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryParamAsNumber {
    /**
     * Set annotation enabled. Enabled by default
     *
     * @return true if annotation enabled
     */
    boolean enabled() default true;
}
