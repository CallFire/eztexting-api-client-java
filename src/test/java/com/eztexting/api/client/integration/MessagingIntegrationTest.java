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
        msg.setDeliveryMethod(DeliveryMethod.STANDARD);
        msg.setPhoneNumbers(Arrays.asList("2132212384", "2132212384"));
        msg.setStampToSend(DateUtils.addMinutes(new Date(), 5));

        SmsMessage response = client.messagingApi().send(msg);
        System.out.println("send sms message response: " + response);
    }

    @Test
    public void sendMms() throws Exception {
        MmsMessage msg = new MmsMessage();
        msg.setMessage("my mms message");
        msg.setSubject("msg subject");
        msg.setFileId(3139L);
        msg.setPhoneNumbers(Arrays.asList("2132212384", "2132212384"));
        msg.setStampToSend(DateUtils.addMinutes(new Date(), 5));

        MmsMessage response = client.messagingApi().send(msg);
        System.out.println("send mms message response: " + response);
    }

    @Test
    public void sendVoice() throws Exception {
        VoiceMessage msg = new VoiceMessage();
        msg.setName("voice broadcast");
        msg.setCallerPhoneNumber("2132212384");
        msg.setVoiceSource("https://eztxting.s3.amazonaws.com/188814/mms/train_1449507791.mp3");
        msg.setPhoneNumbers(Arrays.asList("2132212384", "2132212384"));
        msg.setStampToSend(DateUtils.addMinutes(new Date(), 5));

        VoiceMessage response = client.messagingApi().send(msg);
        System.out.println("send voice message response: " + response);
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
