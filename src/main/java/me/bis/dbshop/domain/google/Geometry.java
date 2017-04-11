package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    private LatLng location;

    public LatLng getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Geometry{" + "location=" + location +
                '}';
    }
}
