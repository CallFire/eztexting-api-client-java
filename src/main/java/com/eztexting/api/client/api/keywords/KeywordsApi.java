package com.eztexting.api.client.api.keywords;

import com.eztexting.api.client.ClientUtils;
import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
import com.eztexting.api.client.api.credits.model.CreditCard;
import com.eztexting.api.client.api.keywords.model.CheckAvailabilityResponse;
import com.eztexting.api.client.api.keywords.model.Keyword;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * API for managing keywords: check availability, rent, configure, cancel
 *
 * @since 1.0
 */
public class KeywordsApi {
    private static final String KEYWORDS_PATH = "/keywords?format=json";
    private static final String KEYWORDS_ITEM_PATH = "/keywords/{}?format=json";
    private static final String CHECK_AVAILABILITY_PATH = "/keywords/new?Keyword={}&format=json";

    private RestApiClient client;

    public KeywordsApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Check whether a Keyword is available to rent on Ez Texting's short code. Please note, we will check
     * availability for the country your account is set to.
     *
     * @param keyword keyword to check
     * @return true if keyword is available to rent, otherwise false returned
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Boolean checkAvailability(String keyword) {
        Validate.notBlank(keyword, "keyword cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("Keyword", keyword);
        String path = StringUtils.replaceOnce(CHECK_AVAILABILITY_PATH, PLACEHOLDER, keyword);
        try {
            return client.get(path, CheckAvailabilityResponse.class, params).getEntry().getAvailable();
            // TODO remove block below after api fixes
        } catch (EzTextingApiException e) {
            if (e.getErrors().contains("Keyword: The keyword '" + keyword + "' is unavailable")) {
                return false;
            }
            throw e;
        }
    }

    /**
     * Rents a Keyword for use on Ez Texting's short code in the country your account is set to send messages to.
     * You may rent a Keyword using a credit card you have stored in your Ez Texting account, or you may pass credit
     * card details when you call the API.
     *
     * @param keyword  keyword for rent
     * @param ccNumber last four digits of any card stored in your Ez Texting account.
     * @return keyword
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Keyword rent(String keyword, String ccNumber) {
        Validate.notBlank(keyword, "keyword cannot be blank");
        Validate.notBlank(ccNumber, "ccNumber cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("Keyword", keyword, "StoredCreditCard", ccNumber);
        return client.post(KEYWORDS_PATH, Keyword.class, params).getEntry();
    }

    /**
     * Rents a Keyword for use on Ez Texting's short code in the country your account is set to send messages to.
     * You may rent a Keyword using a credit card you have stored in your Ez Texting account, or you may pass credit
     * card details when you call the API.
     *
     * @param keyword    keyword for rent
     * @param creditCard credit card for payment
     * @return keyword
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Keyword rent(String keyword, CreditCard creditCard) {
        Validate.notBlank(keyword, "keyword cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("Keyword", keyword);
        return client.post(KEYWORDS_PATH, Keyword.class, creditCard, params).getEntry();
    }

    /**
     * Configures an active Keyword for use on Ez Texting's short code in the country your account is set to send messages to.
     * Similar to update operation
     *
     * @param keyword keyword
     * @return updated keyword
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Keyword setup(Keyword keyword) {
        Validate.notBlank(keyword.getKeyword(), "keyword cannot be blank");
        String path = StringUtils.replaceOnce(KEYWORDS_ITEM_PATH, PLACEHOLDER, keyword.getKeyword());
        return client.post(path, Keyword.class, keyword).getEntry();
    }

    /**
     * Cancels an active Keyword on Ez Texting's short code in the country your account is set to send messages to.
     *
     * @param keyword keyword
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void cancel(String keyword) {
        Validate.notBlank(keyword, "keyword cannot be blank");
        client.delete(StringUtils.replaceOnce(KEYWORDS_ITEM_PATH, PLACEHOLDER, keyword));
    }
}
