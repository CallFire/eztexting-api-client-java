package com.eztexting.api.client;

/**
 * Client constants
 *
 * @since 1.0
 */
public interface ClientConstants {
    String EZ_BASE_PATH_PROPERTY = "com.eztexting.api.client.path";
    String CT_BASE_PATH_PROPERTY = "com.clubtexting.api.client.path";
    String GT_BASE_PATH_PROPERTY = "com.grouptexting.api.client.path";
    String TMC_BASE_PATH_PROPERTY = "com.tellmycell.api.client.path";

    String USER_AGENT_PROPERTY = "com.eztexting.api.client.version";
    String CLIENT_CONFIG_FILE = "/com/eztexting/api/client/eztexting.properties";

    String PLACEHOLDER = "{}";
    String DATE_FORMAT_PATTERN = "MM-dd-yyyy h:mm a";
}
