package com.eztexting.api.client;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.auth.RequestParamAuth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class RestApiClientTest extends AbstractApiTest {
    private RestApiClient client = new RestApiClient(new RequestParamAuth("1", "2"));
    private String expectedJson = getJsonPayload("/common/errorResponse.json");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client.setHttpClient(mockHttpClient);
    }

    @Test
    public void testExpectBadRequestWhen400() throws Exception {
        mockHttpResponse(expectedJson, 400);

        String msg1 = "PhoneNumbers: '(123)45-67' contains characters which are not digits";
        String msg2 = "Subject: Your subject must be under 13 characters";
        String msg3 = "Message: Your message contains characters that are not supported";
        ex.expect(BadRequestException.class);
        ex.expectMessage(msg1);
        ex.expectMessage(msg2);
        ex.expectMessage(msg3);

        client.delete("/");
    }

    @Test(expected = UnauthorizedException.class)
    public void testExpectUnauthorizedWhen401() throws Exception {
        mockHttpResponse(expectedJson, 401);
        client.delete("/");
    }

    @Test(expected = AccessForbiddenException.class)
    public void testExpectAccessForbiddenWhen403() throws Exception {
        mockHttpResponse(expectedJson, 403);
        client.delete("/");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testExpectResourceNotFoundWhen404() throws Exception {
        mockHttpResponse(expectedJson, 404);
        client.delete("/");
    }

    @Test(expected = InternalServerErrorException.class)
    public void testExpectInternalServerErrorWhen500() throws Exception {
        mockHttpResponse(expectedJson, 500);
        client.delete("/");
    }

    @Test(expected = EzTextingApiException.class)
    public void testExpectCallfireApiExceptionInOtherCodeReturned() throws Exception {
        mockHttpResponse(expectedJson, 499);
        client.delete("/");
    }
}
