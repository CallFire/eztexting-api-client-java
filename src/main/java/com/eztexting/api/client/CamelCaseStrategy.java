package com.eztexting.api.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class CamelCaseStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    public static final CamelCaseStrategy CAMEL_CASE_STRATEGY = new CamelCaseStrategy();

    @Override
    public String translate(String propertyName) {
        return propertyName;
    }
}
