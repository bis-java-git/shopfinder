package me.bis.dbshop.domain.net;

import java.math.BigDecimal;

public class ShopFindRequest {

    private BigDecimal currentLatitude;

    private BigDecimal currentLongitude;

    public ShopFindRequest() {}

    public  ShopFindRequest(BigDecimal currentLatitude, BigDecimal currentLongitude) {
        this.currentLatitude=currentLatitude;
        this.currentLongitude=currentLongitude;
    }

    public BigDecimal getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(BigDecimal currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public BigDecimal getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(BigDecimal currentLongitude) {
        this.currentLongitude = currentLongitude;
    }
}
