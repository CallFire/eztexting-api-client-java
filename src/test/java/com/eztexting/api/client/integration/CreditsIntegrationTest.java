package com.eztexting.api.client.integration;

import com.eztexting.api.client.api.credits.model.BuyCreditsRequest;
import com.eztexting.api.client.api.credits.model.BuyCreditsResponse;
import com.eztexting.api.client.api.credits.model.CreditBalance;
import com.eztexting.api.client.api.credits.model.CreditCard.CreditCardType;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CreditsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void checkBalance() throws Exception {
        CreditBalance balance = client.creditsApi().checkBalance();
        System.out.println("balance: " + balance);
    }

    @Test
    public void buyCreditsUsingStoredCreditCard() throws Exception {
        BuyCreditsRequest request = BuyCreditsRequest.withStoredCard("1111").credits(200L).build();
        BuyCreditsResponse response = client.creditsApi().buyCredits(request);
        System.out.println("buy credits with stored cc: " + response);
    }

    @Test
    public void buyCreditsUsingNewCreditCard() throws Exception {
        BuyCreditsRequest request = BuyCreditsRequest.withNewCard()
            .credits(200L)
            .firstName("John")
            .lastName("Doe")
            .state("LA")
            .city("Los Angeles")
            .street("4th Street")
            .country("United States")
            .zip("12345")
            .number("4111111111111111")
            .type(CreditCardType.VISA)
            .month("12")
            .year("2020")
            .securityCode("123")
            .build();
        BuyCreditsResponse response = client.creditsApi().buyCredits(request);
        System.out.println("buy credits with new cc: " + response);
    }
}
