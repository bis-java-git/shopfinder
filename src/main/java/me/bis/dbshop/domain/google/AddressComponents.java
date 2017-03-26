package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponents {

    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    private AddressComponentType[] types;

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setTypes(AddressComponentType[] types) {
        this.types = types;
    }
}
