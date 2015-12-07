package com.eztexting.api.client.api.toolbox.model;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CarrierLookupResponse extends EzTextingModel {
    private String phoneNumber;
    private CarrierName carrierName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CarrierName getCarrierName() {
        return carrierName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("phoneNumber", phoneNumber)
            .append("carrierName", carrierName)
            .toString();
    }
}
