package me.bis.dbshop.domain.google;

public enum AddressComponentType {

    postal_code("postal_code"),

    postal_code_prefix("postal_code_prefix"),

    route("route"),

    political("political"),

    country("country"),

    administrative_area_level_1("administrative_area_level_1"),

    administrative_area_level_2("administrative_area_level_2"),

    postal_town("postal_town");

    AddressComponentType(final String addressComponentType) {
        String addressComponentType1 = addressComponentType;
    }
}
