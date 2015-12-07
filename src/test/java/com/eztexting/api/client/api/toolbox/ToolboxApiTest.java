package com.eztexting.api.client.api.toolbox;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.toolbox.model.CarrierLookupResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class ToolboxApiTest extends AbstractApiTest {

    @Test
    public void carrierLookup() throws Exception {
        String expectedJson = getJsonPayload("/toolbox/toolboxApi/carrierLookup.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CarrierLookupResponse response = client.toolboxApi().carrierLookup("1234567890");
        EzTextingResponse<CarrierLookupResponse> ezResponse = new EzTextingResponse<>("Success", 200, response);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/sending/phone-numbers/1234567890?format=json"));
        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
    }
}
