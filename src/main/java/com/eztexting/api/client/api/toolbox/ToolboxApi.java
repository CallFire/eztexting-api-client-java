package com.eztexting.api.client.api.toolbox;

import com.eztexting.api.client.ClientUtils;
import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
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
     * Get the wireless carrier of a valid mobile phone number (US and Canada)
     *
     * @param phoneNumber phone number
     * @return the wireless carrier of a valid mobile phone number (US and Canada)
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public CarrierLookupResponse carrierLookup(String phoneNumber) {
        Validate.notBlank(phoneNumber, "phoneNumber cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("PhoneNumber", phoneNumber);
        String path = StringUtils.replaceOnce(CARRIER_LOOKUP_PATH, PLACEHOLDER, phoneNumber);
        return client.get(path, CarrierLookupResponse.class, params).getEntry();
    }
}
