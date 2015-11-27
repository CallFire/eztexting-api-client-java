package com.eztexting.api.client.integration;

import com.eztexting.api.client.api.messaging.model.SmsMessage;
import org.junit.Test;

public class MessagingIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testSend() throws Exception {
        SmsMessage msg = new SmsMessage();
        msg.setMessage("my test message");
        msg.setSubject("msg subject");
     //   msg.setPhoneNumbers("msg subject");
        msg.setSubject("msg subject");

    }
}
