package com.eztexting.api.client.api.messaging;

import com.eztexting.api.client.*;
import com.eztexting.api.client.api.messaging.model.DeliveryReport;
import com.eztexting.api.client.api.messaging.model.DeliveryStatus;
import com.eztexting.api.client.api.messaging.model.Message;
import com.eztexting.api.client.api.messaging.model.SendMessageResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * Represents APIs for sending SMS/MMS messages, get short and detailed delivery reports
 *
 * @since 1.0
 */
public class MessagingApi {
    private static final String MESSAGES_PATH = "/sending/messages?format=json";
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
    public SendMessageResponse send(Message message) {
        return client.post(MESSAGES_PATH, SendMessageResponse.class, message).getEntry();
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
