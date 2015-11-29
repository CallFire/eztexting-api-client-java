package com.eztexting.api.client.integration;

import com.eztexting.api.client.EzTextingClient;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class AbstractIntegrationTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();
    protected EzTextingClient client;

    public AbstractIntegrationTest() {
        client = new EzTextingClient("", "");
    }

}
