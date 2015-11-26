package com.eztexting.api.client;

import com.eztexting.api.client.api.messaging.model.MessageType;
import com.eztexting.api.client.api.messaging.model.MmsMessage;
import org.junit.Test;

import java.util.Date;

import static com.eztexting.api.client.ClientUtils.encode;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ClientUtilsTest {

    @Test
    public void testBuildQueryParams() throws Exception {
        MmsMessage mms = new MmsMessage();
        mms.setFileId(123L);
        mms.setMessageType(MessageType.MMS);
        mms.setSubject("test subject");
        mms.setMessage("this is mms message");
        mms.setGroups(asList("group1", "group2", "group3"));
        mms.setPhoneNumbers(asList("1234567890", "2345678900", "3456789000"));
        Date now = new Date();
        mms.setStampToSend(now);
        String queryParams = ClientUtils.buildQueryParams(mms).toString();

        System.out.println("Response: " + queryParams);

        assertThat(queryParams, containsString("FileId=" + "123"));
        assertThat(queryParams, containsString("Groups[]=" + "group1"));
        assertThat(queryParams, containsString("Groups[]=" + "group2"));
        assertThat(queryParams, containsString("Groups[]=" + "group3"));
        assertThat(queryParams, containsString("PhoneNumbers[]=" + "1234567890"));
        assertThat(queryParams, containsString("PhoneNumbers[]=" + "2345678900"));
        assertThat(queryParams, containsString("PhoneNumbers[]=" + "3456789000"));
        assertThat(queryParams, containsString("Subject=" + encode("test subject")));
        assertThat(queryParams, containsString("Message=" + encode("this is mms message")));
        assertThat(queryParams, containsString("MessageTypeID=3"));
        assertThat(queryParams, containsString("StampToSend=" + now.getTime()));
    }
}
