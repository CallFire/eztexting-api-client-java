package com.eztexting.api.client.auth;

import static com.eztexting.api.client.ClientUtils.encode;

/**
 * Implementation of EzTexting auth scheme
 */
public class RequestParamAuth implements Authentication {
    private String authParams;

    /**
     * Constructs EzTexting auth from provided credentials
     *
     * @param username api username
     * @param password api password
     */
    public RequestParamAuth(String username, String password) {
        authParams = "User" + "=" + encode(username) + "&" + "Password" + "=" + encode(password);
    }

    @Override
    public String asParamString() {
        return authParams;
    }
}
