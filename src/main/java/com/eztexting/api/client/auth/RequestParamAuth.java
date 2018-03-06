package com.eztexting.api.client.auth;

import static com.eztexting.api.client.ClientUtils.encode;

/**
 * Implementation of EzTexting auth scheme
 */
public class RequestParamAuth implements Authentication {
    private String username;
    private String password;
    private String authParams;

    /**
     * Constructs EzTexting auth from provided credentials
     *
     * @param username api username
     * @param password api password
     */
    public RequestParamAuth(String username, String password) {
        this.username = username;
        this.password = password;
        authParams = "User" + "=" + encode(username) + "&" + "Password" + "=" + encode(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String asParamString() {
        return authParams;
    }
}
