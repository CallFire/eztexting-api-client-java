package com.eztexting.api.client.api.credits.model;

import com.eztexting.api.client.api.common.model.AbstractBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyCreditsRequest extends CreditCard {
    @JsonProperty("NumberOfCredits")
    private Long credits;
    private String couponCode;
    @JsonProperty("StoredCreditCard")
    private String storedCard;

    private BuyCreditsRequest() {
    }

    /**
     * Create request builder with stored credit card option
     * Set last four digits of any card stored in your Ez Texting account.
     *
     * @param cardNumber last four digits of any card stored in your Ez Texting account.
     * @return builder object
     */
    public static StoredCardRequestBuilder withStoredCard(String cardNumber) {
        BuyCreditsRequest request = new BuyCreditsRequest();
        request.storedCard = cardNumber;
        return new StoredCardRequestBuilder(request);
    }

    /**
     * Create request builder with a new credit card details option
     *
     * @return request builder
     */
    public static NewCardRequestBuilder withNewCard() {
        return new NewCardRequestBuilder();
    }

    public Long getCredits() {
        return credits;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getStoredCard() {
        return storedCard;
    }

    /**
     * Builder with stored credit card option
     */
    @SuppressWarnings("unchecked")
    public abstract static class BuyCreditsRequestBuilder<B extends BuyCreditsRequestBuilder>
        extends AbstractBuilder<BuyCreditsRequest> {

        private BuyCreditsRequestBuilder(BuyCreditsRequest request) {
            super(request);
        }

        /**
         * Set number of credits to purchase
         *
         * @param credits number of credits to purchase
         * @return builder object
         */
        public B credits(Long credits) {
            request.credits = credits;
            return (B) this;
        }

        /**
         * Set coupon or Promotional Code
         *
         * @param couponCode Coupon or Promotional Code
         * @return builder object
         */
        public B couponCode(String couponCode) {
            request.couponCode = couponCode;
            return (B) this;
        }
    }

    /**
     * Builder with stored credit card option
     */
    public static class StoredCardRequestBuilder extends BuyCreditsRequestBuilder<StoredCardRequestBuilder> {
        private StoredCardRequestBuilder(BuyCreditsRequest request) {
            super(request);
        }
    }

    /**
     * Builder with a new credit card details option
     */
    public static class NewCardRequestBuilder extends BuyCreditsRequestBuilder<NewCardRequestBuilder> {
        private NewCardRequestBuilder() {
            super(new BuyCreditsRequest());
        }

        /**
         * Set the cardholder's first name
         *
         * @param firstName the cardholder's first name
         * @return builder object
         */
        public NewCardRequestBuilder firstName(String firstName) {
            request.firstName = firstName;
            return this;
        }

        /**
         * Set the cardholder's last name
         *
         * @param lastName the cardholder's last name
         * @return builder object
         */
        public NewCardRequestBuilder lastName(String lastName) {
            request.lastName = lastName;
            return this;
        }

        /**
         * Set the billing street address
         *
         * @param street the billing street address
         * @return builder object
         */
        public NewCardRequestBuilder street(String street) {
            request.street = street;
            return this;
        }

        /**
         * Set the billing address city
         *
         * @param city the billing address city
         * @return builder object
         */
        public NewCardRequestBuilder city(String city) {
            request.city = city;
            return this;
        }

        /**
         * Set the billing address state/province
         *
         * @param state the billing address state/province
         * @return builder object
         */
        public NewCardRequestBuilder state(String state) {
            request.state = state;
            return this;
        }

        /**
         * Set the billing address zip code
         *
         * @param zip the billing address zip code
         * @return builder object
         */
        public NewCardRequestBuilder zip(String zip) {
            request.zip = zip;
            return this;
        }

        /**
         * Set the billing address country
         *
         * @param country the billing address country
         * @return builder object
         */
        public NewCardRequestBuilder country(String country) {
            request.country = country;
            return this;
        }

        /**
         * Set credit card type: Amex, Discover, MasterCard, Visa
         *
         * @param type credit card type: Amex, Discover, MasterCard, Visa
         * @return builder object
         */
        public NewCardRequestBuilder type(CreditCardType type) {
            request.type = type;
            return this;
        }

        /**
         * Set credit card number
         *
         * @param number credit card number
         * @return builder object
         */
        public NewCardRequestBuilder number(String number) {
            request.number = number;
            return this;
        }

        /**
         * Set credit card security code (CV2)
         *
         * @param code credit card security code (CV2)
         * @return builder object
         */
        public NewCardRequestBuilder securityCode(String code) {
            request.securityCode = code;
            return this;
        }

        /**
         * Set  credit card's expiration month, two digits
         *
         * @param month credit card's expiration month, two digits
         * @return builder object
         */
        public NewCardRequestBuilder month(String month) {
            request.expirationMonth = month;
            return this;
        }

        /**
         * Set credit card's expiration year, four digits
         *
         * @param year credit card's expiration year, four digits
         * @return builder object
         */
        public NewCardRequestBuilder year(String year) {
            request.expirationYear = year;
            return this;
        }
    }
}
