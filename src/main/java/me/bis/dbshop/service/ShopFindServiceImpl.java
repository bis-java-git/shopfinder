package me.bis.dbshop.service;

import ch.qos.logback.classic.Logger;
import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.google.LatLng;
import me.bis.dbshop.service.google.GoogleLocatorService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShopFindServiceImpl implements ShopFindService {

    final static Logger logger = (Logger) LoggerFactory.getLogger(ShopFindServiceImpl.class);

    public static final int INITIAL_CAPACITY = 1000;

    private GoogleLocatorService googleLocatorService;

    private Map<String, Shop> shopDataStore = new ConcurrentHashMap<>(INITIAL_CAPACITY);

    @Autowired
    public ShopFindServiceImpl(GoogleLocatorService googleLocatorService) {
        this.googleLocatorService = googleLocatorService;
    }

    @Override
    public Shop findNearestShop(BigDecimal currentLatitude, BigDecimal currentLongitude) {
        Map<BigDecimal, Shop> distanceMap = new HashMap<>();

        shopDataStore.entrySet().forEach(entry -> {
            BigDecimal distance = googleLocatorService.calculateDistance(currentLatitude,
                    currentLongitude,
                    entry.getValue().getLatitude(),
                    entry.getValue().getLongitude());
            distanceMap.put(distance, entry.getValue());
        });

        Map<BigDecimal, Shop> treeMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

        treeMap.putAll(distanceMap);

        Map.Entry<BigDecimal, Shop> shopEntry = treeMap.entrySet().iterator().next();
        return shopEntry.getValue();
    }

    @Override
    public Shop addShop(Shop shop) {
        LatLng latLng = googleLocatorService.GetLatLng(shop.getShopAddress().getPostCode());
        logger.info("LatLng is {} ", latLng);
        shop.setLongitude(latLng.getLongitude());
        shop.setLatitude(latLng.getLatitude());
        Shop savedShop = shopDataStore.put(shop.getShopName(), shop);
        logger.info("SavedShop is {}", savedShop);
        return savedShop;
    }
}
