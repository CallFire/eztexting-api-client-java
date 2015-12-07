package com.eztexting.api.client.api.messaging;

import com.eztexting.api.client.*;
import com.eztexting.api.client.api.messaging.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * Represents APIs for sending SMS/MMS messages, get short and detailed delivery reports
 *
 * @since 1.0
 */
public class MessagingApi {
    private static final String SEND_TEXT_PATH = "/sending/messages?format=json";
    private static final String SEND_VOICE_PATH = "/voice/messages?format=json";
    private static final String REPORT_PATH = "/sending/reports/{}/?format=json";
    private static final String DETAILED_REPORT_PATH = "/sending/reports/{}/view-details?format=json";

    private RestApiClient client;

    public MessagingApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Sends SMS/MMS messages via the short code 313131 (393939 In Canada) to a single phone number or an array of phone numbers.
     *
     * @param message message to send
     * @return created message with additional info
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    @SuppressWarnings("unchecked")
    public <T extends TextMessage> T send(T message) {
        Validate.notNull(message.getDeliveryMethod(), "Delivery method must be set");
        if (message instanceof SmsMessage) {
            return (T) client.post(SEND_TEXT_PATH, SmsMessage.class, message).getEntry();
        } else if (message instanceof MmsMessage) {
            return (T) client.post(SEND_TEXT_PATH, MmsMessage.class, message).getEntry();
        } else {
            throw new IllegalStateException("Message of type " + message.getClass().getName() + " isn't supported");
        }
    }

    /**
     * Sends voice broadcast messages to an array of phone numbers or a Group in your Ez Texting account. You
     * can use a file stored in your Ez Texting account as the source, or include the URL of a compatible file
     * in the request.
     *
     * @param message voice message request
     * @return response object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public VoiceMessage send(VoiceMessage message) {
        return client.post(SEND_VOICE_PATH, VoiceMessage.class, message).getEntry();
    }

    /**
     * Get a report for specific delivery status of a message you have sent.
     *
     * @param id message id
     * @return delivery report
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public DeliveryReport getReport(long id) {
        String path = StringUtils.replace(REPORT_PATH, PLACEHOLDER, String.valueOf(id));
        return client.get(path, DeliveryReport.class).getEntry();
    }

    /**
     * Get a report for specific delivery status of a message you have sent.
     *
     * @param id     message id
     * @param status delivery status to sort by
     * @return delivery report
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public List<Long> getDetailedReport(long id, DeliveryStatus status) {
        String path = StringUtils.replace(DETAILED_REPORT_PATH, PLACEHOLDER, String.valueOf(id));
        List<NameValuePair> params = ClientUtils.asParams("status", status.toString());
        return client.get(path, Long.class, params).getEntries();
    }
}
