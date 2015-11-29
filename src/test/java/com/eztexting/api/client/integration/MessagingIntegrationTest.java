package com.eztexting.api.client.integration;

import com.eztexting.api.client.api.messaging.model.*;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Ignore
public class MessagingIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void sendSms() throws Exception {
        SmsMessage msg = new SmsMessage();
        msg.setMessage("my test message");
        msg.setSubject("msg subject");
        msg.setMessageType(MessageType.STANDARD);
        msg.setPhoneNumbers(Arrays.asList("6508926110", "6508926110"));
        msg.setStampToSend(DateUtils.addMinutes(new Date(), 5));

        SendMessageResponse response = client.messagingApi().send(msg);
        System.out.println("send message response: " + response);
    }

    @Test
    public void getReport() throws Exception {
        DeliveryReport report = client.messagingApi().getReport(56491730L);
        System.out.println("getReport response: " + report);

    }

    @Test
    public void getDetailedReport() throws Exception {
        List<Long> report = client.messagingApi().getDetailedReport(56491730L, DeliveryStatus.BOUNCED);
        System.out.println("getDetailedReport response: " + report);

    }
}
