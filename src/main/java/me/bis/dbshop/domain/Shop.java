package me.bis.dbshop.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Holds all Shop address and related data
 */
public class Shop {

    private String shopName;

    private ShopAddress shopAddress;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ShopAddress getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(ShopAddress shopAddress) {
        this.shopAddress = shopAddress;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return Objects.equals(getShopName(), shop.getShopName()) &&
                Objects.equals(getShopAddress(), shop.getShopAddress()) &&
                Objects.equals(getLatitude(), shop.getLatitude()) &&
                Objects.equals(getLongitude(), shop.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShopName(), getShopAddress(), getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "Shop{" + "shopName='" + shopName + '\'' +
                ", shopAddress=" + shopAddress +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
