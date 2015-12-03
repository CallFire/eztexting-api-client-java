package com.eztexting.api.client.api.credits;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.credits.model.BuyCreditsRequest;
import com.eztexting.api.client.api.credits.model.BuyCreditsResponse;
import com.eztexting.api.client.api.credits.model.CreditBalance;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class CreditsApiTest extends AbstractApiTest {

    @Test
    public void checkBalance() throws Exception {
        String expectedJson = getJsonPayload("/credits/creditsApi/checkBalance.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CreditBalance balance = client.creditsApi().checkBalance();
        EzTextingResponse<CreditBalance> ezResponse = new EzTextingResponse<>("Success", 200, balance);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/billing/credits/get?format=json"));
        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
    }

    @Test
    public void buyCreditsUsingStoredCreditCard() throws Exception {
        String expectedJson = getJsonPayload("/credits/creditsApi/buyCredits.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);
        BuyCreditsRequest request = BuyCreditsRequest.withStoredCard("4533")
            .credits(2000L)
            .couponCode("ABX32WE")
            .build();
        BuyCreditsResponse response = client.creditsApi().buyCredits(request);
        EzTextingResponse<BuyCreditsResponse> ezResponse = new EzTextingResponse<>("Success", 201, response);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("NumberOfCredits=2000"));
        assertThat(requestBody, containsString("CouponCode=ABX32WE"));
        assertThat(requestBody, containsString("StoredCreditCard=4533"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void buyCreditsUsingNewCreditCard() throws Exception {
        String expectedJson = getJsonPayload("/credits/creditsApi/buyCredits.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);
        BuyCreditsRequest request = BuyCreditsRequest.withNewCard()
            .credits(2000L)
            .couponCode("ABX32WE")
            .firstName("John")
            .lastName("Doe")
            .state("LA")
            .securityCode("123")
            .build();
        BuyCreditsResponse response = client.creditsApi().buyCredits(request);
        EzTextingResponse<BuyCreditsResponse> ezResponse = new EzTextingResponse<>("Success", 201, response);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("NumberOfCredits=2000"));
        assertThat(requestBody, containsString("CouponCode=ABX32WE"));
        assertThat(requestBody, containsString("FirstName=John"));
        assertThat(requestBody, containsString("LastName=Doe"));
        assertThat(requestBody, containsString("State=LA"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }
}
