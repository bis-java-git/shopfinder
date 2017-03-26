package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Results {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonProperty("address_components")
    private AddressComponents[] addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("geometry")
    private Geometry geometry;

    @JsonProperty("types")
    private AddressType[] types;

    @JsonIgnore
    private boolean partialMatch;

    @JsonProperty("place_id")
    private String placeId;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AddressComponents[] getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(AddressComponents[] addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public AddressType[] getTypes() {
        return types;
    }

    public void setTypes(AddressType[] types) {
        this.types = types;
    }

    public boolean isPartialMatch() {
        return partialMatch;
    }

    public void setPartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Results{");
        sb.append("addressComponents=").append(Arrays.toString(addressComponents));
        sb.append(", formattedAddress='").append(formattedAddress).append('\'');
        sb.append(", geometry=").append(geometry);
        sb.append(", types=").append(Arrays.toString(types));
        sb.append(", partialMatch=").append(partialMatch);
        sb.append(", placeId='").append(placeId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
