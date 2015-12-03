package com.eztexting.api.client.api.credits.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class BuyCreditsResponse extends CreditBalance {
    private Long boughtCredits;
    private BigDecimal amount;
    private BigDecimal discount;

    public Long getBoughtCredits() {
        return boughtCredits;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("boughtCredits", boughtCredits)
            .append("amount", amount)
            .append("discount", discount)
            .toString();
    }
}
