package me.bis.dbshop.domain;

import java.util.Objects;

/**
 * Holds shop address data
 */
public class ShopAddress {

    private Integer number;

    private String postCode;

    public ShopAddress() {
    }

    public ShopAddress(Integer number, String postCode) {
        this.number = number;
        this.postCode = postCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopAddress)) return false;
        ShopAddress that = (ShopAddress) o;
        return Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getPostCode(), that.getPostCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getPostCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShopAddress{");
        sb.append("number=").append(number);
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
