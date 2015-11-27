package com.eztexting.api.client.api.messaging;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.messaging.model.MessageType;
import com.eztexting.api.client.api.messaging.model.MmsMessage;
import com.eztexting.api.client.api.messaging.model.SendMessageResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Date;

import static com.eztexting.api.client.ClientUtils.encode;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class MessagingApiTest extends AbstractApiTest {

    @Test
    public void send() throws Exception {
        String expectedJson = getJsonPayload("/messaging/messagingApi/send.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        MmsMessage mms = new MmsMessage();
        mms.setFileId(123L);
        mms.setGroups(asList("group1", "group2", "group3"));
        mms.setPhoneNumbers(asList("1234567890", "2345678900", "3456789000"));
        mms.setSubject("test subject");
        mms.setMessage("this is mms message");
        mms.setMessageType(MessageType.MMS);
        Date now = new Date();
        mms.setStampToSend(now);
        SendMessageResponse response = client.messagingApi().send(mms);
        EzTextingResponse<SendMessageResponse> ezResponse = new EzTextingResponse<>("Success", 201, response);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
        assertThat(requestBody, containsString("FileId=123"));
        assertThat(requestBody, containsString("Subject=" + encode("test subject")));
        assertThat(requestBody, containsString("Message=" + encode("this is mms message")));
        assertThat(requestBody, containsString("Groups[]=group1"));
        assertThat(requestBody, containsString("Groups[]=group2"));
        assertThat(requestBody, containsString("Groups[]=group3"));
        assertThat(requestBody, containsString("PhoneNumbers[]=1234567890"));
        assertThat(requestBody, containsString("PhoneNumbers[]=2345678900"));
        assertThat(requestBody, containsString("PhoneNumbers[]=3456789000"));
        assertThat(requestBody, containsString("MessageTypeID=3"));
        assertThat(requestBody, containsString("StampToSend=" + now.getTime()));
    }
}
