package com.eztexting.api.client.integration;

import com.eztexting.api.client.api.toolbox.model.CarrierLookupResponse;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ToolboxIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void carrierLookup() throws Exception {
        CarrierLookupResponse response = client.toolboxApi().carrierLookup("2132212384");
        System.out.println("lookup response: " + response);
    }
}
