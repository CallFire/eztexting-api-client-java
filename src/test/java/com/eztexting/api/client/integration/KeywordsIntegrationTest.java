package com.eztexting.api.client.integration;

import com.eztexting.api.client.AccessForbiddenException;
import com.eztexting.api.client.api.keywords.model.CreditCard;
import com.eztexting.api.client.api.keywords.model.Keyword;
import com.eztexting.api.client.api.messaging.model.DeliveryMethod;
import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class KeywordsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void checkAvailability() throws Exception {
        System.out.println("EZAYW69417: " + client.keywordsApi().checkAvailability("EZAYW69417"));
        System.out.println("Superman: " + client.keywordsApi().checkAvailability("Superman"));
    }

    @Test
    public void setup() throws Exception {
        Keyword keyword = new Keyword();
        keyword.setKeyword("EZAYW69417");
        keyword.setConfirmMessage(new SimpleMessage(DeliveryMethod.EXPRESS, "subj", "confirm updated"));
        Keyword updated = client.keywordsApi().setup(keyword);
        System.out.println("Updated keyword: " + updated);
    }

    @Test
    public void rentWithStoredCreditCard() throws Exception {
        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName("Winnie");
        creditCard.setLastName("The Pooh");
        creditCard.setStreet("Hollow tree, under the name of Mr. Sanders");
        creditCard.setCity("Hundred Acre Woods");
        creditCard.setState("New York");
        creditCard.setZip("12345");
        creditCard.setCountry("US");
        creditCard.setType(CreditCard.CreditCardType.VISA);
        creditCard.setNumber("4111111111111111");
        creditCard.setSecurityCode("123");
        creditCard.setExpirationMonth("10");
        creditCard.setExpirationYear("2017");
        Keyword updated = client.keywordsApi().rent("mytest", creditCard);
        System.out.println("rent keyword: " + updated);
    }

    @Test
    public void rentWithNewCreditCard() throws Exception {
        Keyword updated = client.keywordsApi().rent("mytest", "1111");
        System.out.println("rent keyword: " + updated);
    }

    @Test
    public void cancel() throws Exception {
        ex.expect(AccessForbiddenException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.keywordsApi().cancel("mytest");
    }
}
