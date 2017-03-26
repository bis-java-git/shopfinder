package me.bis.dbshop.service;

import me.bis.dbshop.domain.Shop;

import java.math.BigDecimal;

/**
 * Shop find service public API
 */
public interface ShopFindService {

    Shop findNearestShop(BigDecimal currentLatitude, BigDecimal Longitude);

    Shop addShop(Shop shop);
}
