package com.eztexting.api.client.api.credits;

import com.eztexting.api.client.*;
import com.eztexting.api.client.api.credits.model.BuyCreditsRequest;
import com.eztexting.api.client.api.credits.model.BuyCreditsResponse;
import com.eztexting.api.client.api.credits.model.CreditBalance;

/**
 * API for managing credits in your account
 *
 * @since 1.0
 */
public class CreditsApi {
    private static final String CHECK_BALANCE_PATH = "/billing/credits/get?format=json";
    private static final String BUY_CREDITS_PATH = "/billing/credits?format=json";

    private RestApiClient client;

    public CreditsApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Checks credit balances on your account.
     *
     * @return credit balance
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public CreditBalance checkBalance() {
        return client.get(CHECK_BALANCE_PATH, CreditBalance.class).getEntry();
    }

    /**
     * Buys more credits for your account. You may purchase credits using a credit card you have stored in your
     * Ez Texting account, or you may pass credit card details when you call the API.
     *
     * @param request request object
     * @return amount spent and credit balance
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public BuyCreditsResponse buyCredits(BuyCreditsRequest request) {
        return client.post(BUY_CREDITS_PATH, BuyCreditsResponse.class, request).getEntry();
    }

}
