package com.eztexting.api.client.auth;

/**
 * Provides authentication interface to client for different auth types
 */
public interface Authentication {
    /**
     * Get user login as string
     *
     * @return string
     */
    String getUsername();

    /**
     * Get user password as string
     *
     * @return string
     */
    String getPassword();

    /**
     * Get authentication info as key-value string
     *
     * @return string like "User=${username}&amp;Password=${password}"
     */
    String asParamString();
}
