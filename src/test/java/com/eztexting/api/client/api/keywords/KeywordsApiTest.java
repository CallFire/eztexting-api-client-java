package com.eztexting.api.client.api.keywords;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.keywords.model.CheckAvailabilityResponse;
import com.eztexting.api.client.api.credits.model.CreditCard;
import com.eztexting.api.client.api.credits.model.CreditCard.CreditCardType;
import com.eztexting.api.client.api.keywords.model.Keyword;
import com.eztexting.api.client.api.messaging.model.DeliveryMethod;
import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;

import static com.eztexting.api.client.ClientUtils.encode;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class KeywordsApiTest extends AbstractApiTest {

    @Test
    public void checkAvailability() throws Exception {
        String expectedJson = getJsonPayload("/keywords/keywordsApi/checkAvailability.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Boolean isAvailable = client.keywordsApi().checkAvailability("NewKw");
        assertTrue(isAvailable);
        CheckAvailabilityResponse response = new CheckAvailabilityResponse("honey", true);
        EzTextingResponse<CheckAvailabilityResponse> ezResponse = new EzTextingResponse<>("Success", 200, response);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
        assertThat(arg.getURI().toString(), containsString("/new?Keyword=NewKw&"));
    }

    @Test
    public void checkAvailabilityBlankKeyword() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("keyword cannot be blank");
        client.keywordsApi().setup(new Keyword());
    }

    @Test
    public void rentWithStoredCreditCard() throws Exception {
        String expectedJson = getJsonPayload("/keywords/keywordsApi/rent.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Keyword created = client.keywordsApi().rent("NewKw", "1234");
        EzTextingResponse<Keyword> ezResponse = new EzTextingResponse<>("Success", 201, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Keyword=NewKw"));
        assertThat(requestBody, containsString("StoredCreditCard=1234"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void rentWithStoredCreditCardNullKeyword() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("keyword cannot be blank");
        client.keywordsApi().rent(null, "1234");

        ex.expect(NullPointerException.class);
        ex.expectMessage("ccNumber cannot be blank");
        client.keywordsApi().rent("NewKw", "");
    }

    @Test
    public void rentWithNewCreditCard() throws Exception {
        String expectedJson = getJsonPayload("/keywords/keywordsApi/rent.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName("Winnie");
        creditCard.setLastName("The Pooh");
        creditCard.setStreet("Hollow tree, under the name of Mr. Sanders");
        creditCard.setCity("Hundred Acre Woods");
        creditCard.setState("New York");
        creditCard.setZip("12345");
        creditCard.setCountry("US");
        creditCard.setType(CreditCardType.VISA);
        creditCard.setNumber("4111111111111111");
        creditCard.setSecurityCode("123");
        creditCard.setExpirationMonth("10");
        creditCard.setExpirationYear("2017");
        Keyword created = client.keywordsApi().rent("NewKw", creditCard);
        EzTextingResponse<Keyword> ezResponse = new EzTextingResponse<>("Success", 201, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Keyword=NewKw"));
        assertThat(requestBody, containsString("FirstName=Winnie"));
        assertThat(requestBody, containsString("LastName=" + encode("The Pooh")));
        assertThat(requestBody, containsString("Street=" + encode("Hollow tree, under the name of Mr. Sanders")));
        assertThat(requestBody, containsString("City=" + encode("Hundred Acre Woods")));
        assertThat(requestBody, containsString("State=" + encode("New York")));
        assertThat(requestBody, containsString("Zip=12345"));
        assertThat(requestBody, containsString("Country=US"));
        assertThat(requestBody, containsString("CreditCardTypeID=Visa"));
        assertThat(requestBody, containsString("Number=4111111111111111"));
        assertThat(requestBody, containsString("SecurityCode=123"));
        assertThat(requestBody, containsString("ExpirationMonth=10"));
        assertThat(requestBody, containsString("ExpirationYear=2017"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void rentWithNewCreditCardBlankKeyword() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage("keyword cannot be blank");
        client.keywordsApi().rent(" ", new CreditCard());
    }

    @Test
    public void setup() throws Exception {
        String expectedJson = getJsonPayload("/keywords/keywordsApi/setup.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        SimpleMessage joinMsg = new SimpleMessage(DeliveryMethod.EXPRESS, "subject",
            "Thank you for joining our text list. For Help Reply HELP. Msg&data rates may apply.");
        SimpleMessage confirmMsg = new SimpleMessage(DeliveryMethod.EXPRESS, "subject",
            "You already joined but thanks for texting in again.");
        SimpleMessage alternateReply = new SimpleMessage(DeliveryMethod.EXPRESS,
            "subject", "You already joined but thanks for texting in again.");

        Keyword keyword = new Keyword();
        keyword.setKeyword("NewKw");
        keyword.setId(147258369L);
        keyword.setEnableDoubleOptIn(true);
        keyword.setEnableAlternateReply(true);
        keyword.setConfirmMessage(confirmMsg);
        keyword.setJoinMessage(joinMsg);
        keyword.setAlternateReply(alternateReply);
        keyword.setForwardEmail("honey@bear-alliance.co.uk");
        keyword.setForwardUrl("http://bear-alliance.co.uk/honey-donations/");
        keyword.setContactGroups(Arrays.asList("Friends", "Family"));
        Keyword updated = client.keywordsApi().setup(keyword);
        EzTextingResponse<Keyword> ezResponse = new EzTextingResponse<>("Success", 200, updated);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/keywords/NewKw?"));
        assertThat(requestBody, containsString("Keyword=NewKw"));
        assertThat(requestBody, containsString("EnableDoubleOptIn=true"));
        assertThat(requestBody, containsString("EnableAlternateReply=true"));
        assertThat(requestBody, containsString("ConfirmMessage=" + encode(confirmMsg.getMessage())));
        assertThat(requestBody, containsString("JoinMessage=" + encode(joinMsg.getMessage())));
        assertThat(requestBody, containsString("ForwardEmail=" + encode("honey@bear-alliance.co.uk")));
        assertThat(requestBody, containsString("ForwardUrl=" + encode("http://bear-alliance.co.uk/honey-donations/")));
        assertThat(requestBody, containsString("ContactGroupIDs[]=Friends"));
        assertThat(requestBody, containsString("ContactGroupIDs[]=Family"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void setupBlankKeyword() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("keyword cannot be blank");
        client.keywordsApi().setup(new Keyword());
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.keywordsApi().cancel("TestKw");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/keywords/TestKw?"));
    }

    @Test
    public void deleteBlankKeyword() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage("keyword cannot be blank");
        client.keywordsApi().cancel("");
    }
}
