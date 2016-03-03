package com.eztexting.api.client;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.auth.RequestParamAuth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class RestApiClientTest extends AbstractApiTest {
    private RestApiClient client = new RestApiClient(Brand.EZ, new RequestParamAuth("1", "2"));
    private String expectedJson = getJsonPayload("/common/errorResponse.json");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client.setHttpClient(mockHttpClient);
    }

    @Test
    public void expectBadRequestWhen400() throws Exception {
        mockHttpResponse(expectedJson, 400);

        String msg1 = "PhoneNumbers: '(123)45-67' contains characters which are not digits";
        String msg2 = "Subject: Your subject must be under 13 characters";
        String msg3 = "Message: Your message contains characters that are not supported";
        ex.expect(EzTextingApiException.class);
        ex.expectMessage(msg1);
        ex.expectMessage(msg2);
        ex.expectMessage(msg3);

        client.delete("/");
    }

    // TODO vmikhailov fix code & tests after server-side fixes
    //    @Test(expected = UnauthorizedException.class)
    @Test(expected = EzTextingApiException.class)
    public void expectUnauthorizedWhen401() throws Exception {
        mockHttpResponse(expectedJson, 401);
        client.delete("/");
    }

    @Test(expected = EzTextingApiException.class)
    public void expectAccessForbiddenWhen403() throws Exception {
        mockHttpResponse(expectedJson, 403);
        client.delete("/");
    }

    @Test(expected = EzTextingApiException.class)
    public void expectResourceNotFoundWhen404() throws Exception {
        mockHttpResponse(expectedJson, 404);
        client.delete("/");
    }

    @Test(expected = EzTextingApiException.class)
    public void expectInternalServerErrorWhen500() throws Exception {
        mockHttpResponse(expectedJson, 500);
        client.delete("/");
    }

    @Test(expected = EzTextingApiException.class)
    public void expectCallfireApiExceptionInOtherCodeReturned() throws Exception {
        mockHttpResponse(expectedJson, 499);
        client.delete("/");
    }

    @Test
    public void differentBrandBaseUrls() throws Exception {
        assertEquals("https://app.eztexting.com", new RestApiClient(Brand.EZ, null).getApiBasePath());
        assertEquals("https://app.clubtexting.com", new RestApiClient(Brand.CT, null).getApiBasePath());
        assertEquals("https://app.grouptexting.com", new RestApiClient(Brand.GT, null).getApiBasePath());
        assertEquals("https://app.tellmycell.com", new RestApiClient(Brand.TMC, null).getApiBasePath());
    }
}
