package com.eztexting.api.client;

public enum Brand {
    EZ(ClientConstants.EZ_BASE_PATH_PROPERTY),
    CT(ClientConstants.CT_BASE_PATH_PROPERTY),
    GT(ClientConstants.GT_BASE_PATH_PROPERTY),
    TMC(ClientConstants.TMC_BASE_PATH_PROPERTY);

    private String property;

    Brand(String property) {
        this.property = property;
    }

    public String getBasePathProperty() {
        return property;
    }

    @Override
    public String toString() {
        return property;
    }
}
