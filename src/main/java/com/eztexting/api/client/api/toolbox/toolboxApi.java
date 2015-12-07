package com.eztexting.api.client.api.toolbox;

import com.eztexting.api.client.*;
import com.eztexting.api.client.api.toolbox.model.CarrierLookupResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * Different helper APIs
 *
 * @since 1.0
 */
public class ToolboxApi {
    private static final String CARRIER_LOOKUP_PATH = "/sending/phone-numbers/{}?format=json";

    private RestApiClient client;

    public ToolboxApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Get the wireless carrier of a valid mobile phone number (US & Canada)
     *
     * @param phoneNumber phone number
     * @return the wireless carrier of a valid mobile phone number (US & Canada)
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public CarrierLookupResponse carrierLookup(String phoneNumber) {
        Validate.notBlank(phoneNumber, "phoneNumber cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("PhoneNumber", phoneNumber);
        String path = StringUtils.replaceOnce(CARRIER_LOOKUP_PATH, PLACEHOLDER, phoneNumber);
        return client.get(path, CarrierLookupResponse.class, params).getEntry();
    }
}
