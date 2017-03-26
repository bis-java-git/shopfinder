package me.bis.dbshop.domain.net;

import me.bis.dbshop.domain.ShopAddress;

/**
 * Request from client to add shop address details
 */
public class ShopAddRequest {

    private String shopName;

    private ShopAddress shopAddress;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShopAddRequest{");
        sb.append("shopName='").append(shopName).append('\'');
        sb.append(", shopAddress=").append(shopAddress);
        sb.append('}');
        return sb.toString();
    }
}
