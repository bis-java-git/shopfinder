package me.bis.dbshop.domain.google;

public class Bounds {

    private LatLng northeast;

    private LatLng southwest;

    public void setNortheast(LatLng northeast) {
        this.northeast = northeast;
    }

    public void setSouthwest(LatLng southwest) {
        this.southwest = southwest;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bounds{");
        sb.append("northeast=").append(northeast);
        sb.append(", southwest=").append(southwest);
        sb.append('}');
        return sb.toString();
    }
}
