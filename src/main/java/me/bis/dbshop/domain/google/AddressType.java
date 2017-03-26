package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AddressType {

    postal_code("postal_code"),

    postal_code_prefix("postal_code_prefix");

    @JsonProperty("address_type")
    private final String addressType;

    AddressType(final String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return addressType;
    }
}
