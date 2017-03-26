package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {

    private Bounds bounds;

    private LatLng location;

    @JsonProperty("location_type")
    private LocationType locationType;

    private Bounds viewport;

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public void setViewport(Bounds viewport) {
        this.viewport = viewport;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Geometry{");
        sb.append("bounds=").append(bounds);
        sb.append(", location=").append(location);
        sb.append(", locationType=").append(locationType);
        sb.append(", viewport=").append(viewport);
        sb.append('}');
        return sb.toString();
    }
}
