package com.eztexting.api.client.integration;

import com.eztexting.api.client.EzTextingClient;

public class AbstractIntegrationTest {
    protected EzTextingClient client;

    public AbstractIntegrationTest() {
        client = new EzTextingClient("", "");
    }

}
