package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResults {

    @JsonProperty("geometry")
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        return  "Results{" + "geometry=" + geometry +
                '}';
    }
}
