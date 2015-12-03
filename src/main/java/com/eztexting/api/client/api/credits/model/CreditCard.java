package com.eztexting.api.client.api.credits.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CreditCard extends EzTextingModel {
    protected String firstName;
    protected String lastName;
    protected String street;
    protected String city;
    protected String state;
    protected String zip;
    protected String country;
    @JsonProperty("CreditCardTypeID")
    protected CreditCardType type;
    protected String number;
    protected String securityCode;
    protected String expirationMonth;
    protected String expirationYear;

    public enum CreditCardType {
        AMEX("Amex"), DISCOVER("Discover"), MASTERCARD("MasterCard"), VISA("Visa");

        private String type;

        CreditCardType(String property) {
            this.type = property;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("street", street)
            .append("city", city)
            .append("state", state)
            .append("zip", zip)
            .append("country", country)
            .append("type", type)
            .append("number", number)
            .append("securityCode", securityCode)
            .append("expirationMonth", expirationMonth)
            .append("expirationYear", expirationYear)
            .toString();
    }
}
