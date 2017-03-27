package me.bis.dbshop.service;

import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.ShopAddress;
import me.bis.dbshop.domain.google.LatLng;
import me.bis.dbshop.service.google.GoogleLocationServiceImpl;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Shop service test for follwoing
 * Adding shop
 * Finding nearest shop
 */
public class ShopFindServiceTest {

    @Mock
    private GoogleLocationServiceImpl googleLocationService = mock(GoogleLocationServiceImpl.class);

    private ShopFindService shopFindService = new ShopFindServiceImpl(googleLocationService);

    private Shop createShop(String shopName,
                            Integer shopNumber,
                            String shopPostCode) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddress(new ShopAddress(shopNumber, shopPostCode));
        when(googleLocationService.GetLatLng(shopPostCode)).thenReturn(new LatLng(BigDecimal.TEN, BigDecimal.ONE));
        Shop shopReturned = shopFindService.addShop(shop);
        verify(googleLocationService, atLeastOnce()).GetLatLng(shopPostCode);

        return shopReturned;
    }

    private Shop createAndGetShop(String shopName,
                                  Integer shopNumber,
                                  String shopPostCode,
                                  BigDecimal latitude,
                                  BigDecimal longitude) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddress(new ShopAddress(shopNumber, shopPostCode));
        when(googleLocationService.GetLatLng(shopPostCode)).thenReturn(new LatLng(latitude,
                longitude));
        shopFindService.addShop(shop);
        verify(googleLocationService, atLeastOnce()).GetLatLng(shopPostCode);
        return shop;
    }

    @Test
    public void addShopTest() {
        String UNIQUE_NAME_1 = "Unique Name 1";
        String POST_CODE = "TN2 5HJ";
        int SHOP_NUMBER = 1;
        Shop shop = createShop(UNIQUE_NAME_1, SHOP_NUMBER, POST_CODE);
        assertThat(shop, is(nullValue()));
    }

    @Test
    public void replaceOldShopPostCodeTest() {
        int SHOP_NUMBER = 1;
        String UNIQUE_NAME_1 = "Unique Name 1";
        String POST_CODE = "TN2 5HJ";
        Shop shop = createShop(UNIQUE_NAME_1, SHOP_NUMBER, POST_CODE);
        assertThat(shop, is(nullValue()));

        String NEW_POST_CODE = "TN3 5HJ";
        Shop duplicateShop = createShop(UNIQUE_NAME_1, SHOP_NUMBER, NEW_POST_CODE);
        Shop shopReturned = shopFindService.addShop(duplicateShop);
        assertThat(shopReturned, is(notNullValue()));
        assertThat(duplicateShop.equals(shopReturned), is(false));
    }

    @Test
    public void addMoreThanOneShopTest() {
        String UNIQUE_NAME_1 = "Unique Name 1";
        String UNIQUE_NAME_2 = "Unique Name 2";
        int SHOP_NUMBER = 1;
        String POST_CODE = "TN2 5HJ";
        Shop shop = createShop(UNIQUE_NAME_1, SHOP_NUMBER, POST_CODE);
        assertThat(shop, is(nullValue()));
        String NEW_POST_CODE = "TN3 5HJ";
        Shop duplicateShop = createShop(UNIQUE_NAME_2, SHOP_NUMBER, NEW_POST_CODE);
        assertThat(duplicateShop, is(nullValue()));
    }

    @Test
    public void populateLatitudeAndLongitude() {
        String UNIQUE_NAME_1 = "Unique Name 1";
        int SHOP_NUMBER = 1;
        String POST_CODE = "TN2 5HJ";
        Shop shop = createShop(UNIQUE_NAME_1, SHOP_NUMBER, POST_CODE);
        assertThat(shop, is(nullValue()));
    }

    @Test
    public void findNearestShopTest() {

        String CR0_POSTCODE = "CR0";
        String SW17_POSTCODE = "SW17";
        String TW6_POSTCODE = "TW6";

        String SW17_SHOP_NAME = "BIS Balham";
        String CR0_SHOP_NAME = "BIS Croydon";
        String TW6_SHOP_NAME = "BIS Heathrow";

        Integer TW6_SHOP_NUMBER = 1;
        BigDecimal TN2_LATITUDE = new BigDecimal("51.1424544");
        BigDecimal TN2_LONGITUDE = new BigDecimal("0.3077243");

        BigDecimal SW17_LATITUDE = new BigDecimal("51.4334645");
        BigDecimal SW17_LONGITUDE = new BigDecimal("-0.1669558");

        BigDecimal CR0_LATITUDE = new BigDecimal("51.3627294");
        BigDecimal CR0_LONGITUDE = new BigDecimal("-0.0642075");

        BigDecimal TW6_LATITUDE = new BigDecimal("51.4668442");
        BigDecimal TW6_LONGITUDE = new BigDecimal("-0.4529518");

        BigDecimal TN2_TW6_EXPECTED_DISTANCE = new BigDecimal("39.7720607289");
        BigDecimal TN2_SW17_EXPECTED_DISTANCE = new BigDecimal("28.7215165660");
        BigDecimal TN2_CR0_EXPECTED_DISTANCE = new BigDecimal("22.1423162272");

        Shop tw6Shop = createAndGetShop(TW6_SHOP_NAME,
                TW6_SHOP_NUMBER,
                TW6_POSTCODE,
                TW6_LATITUDE,
                TW6_LONGITUDE
        );
        assertThat(tw6Shop, is(notNullValue()));
        assertThat(tw6Shop.getLatitude(), is(notNullValue()));
        assertThat(tw6Shop.getLongitude(), is(notNullValue()));

        Shop cr0Shop = createAndGetShop(CR0_SHOP_NAME,
                2,
                CR0_POSTCODE,
                CR0_LATITUDE,
                CR0_LONGITUDE
        );
        assertThat(cr0Shop, is(notNullValue()));
        assertThat(cr0Shop.getLatitude(), is(notNullValue()));
        assertThat(cr0Shop.getLongitude(), is(notNullValue()));

        Shop sw17Shop = createAndGetShop(SW17_SHOP_NAME,
                3,
                SW17_POSTCODE,
                SW17_LATITUDE,
                SW17_LONGITUDE
        );
        assertThat(sw17Shop, is(notNullValue()));
        assertThat(sw17Shop.getLatitude(), is(notNullValue()));
        assertThat(sw17Shop.getLongitude(), is(notNullValue()));

        when(googleLocationService.calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, TW6_LATITUDE, TW6_LONGITUDE)).thenReturn(TN2_TW6_EXPECTED_DISTANCE);
        when(googleLocationService.calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, SW17_LATITUDE, SW17_LONGITUDE)).thenReturn(TN2_SW17_EXPECTED_DISTANCE);
        when(googleLocationService.calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, CR0_LATITUDE, CR0_LONGITUDE)).thenReturn(TN2_CR0_EXPECTED_DISTANCE);

        Shop nearestShop = shopFindService.findNearestShop(TN2_LATITUDE,
                TN2_LONGITUDE);

        assertThat(nearestShop, is(notNullValue()));
        assertThat(nearestShop.getShopName(), is(cr0Shop.getShopName()));

        verify(googleLocationService, atLeastOnce()).calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, TW6_LATITUDE, TW6_LONGITUDE);
        verify(googleLocationService, atLeastOnce()).calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, CR0_LATITUDE, CR0_LONGITUDE);
        verify(googleLocationService, atLeastOnce()).calculateDistance(TN2_LATITUDE, TN2_LONGITUDE, SW17_LATITUDE, SW17_LONGITUDE);
    }

    @Test(expected = NoSuchElementException.class)
    public void findNearestShopExceptionTest() {
        BigDecimal TN2_LATITUDE = new BigDecimal("51.1424544");
        BigDecimal TN2_LONGITUDE = new BigDecimal("0.3077243");
        shopFindService.findNearestShop(TN2_LATITUDE,
                TN2_LONGITUDE);
    }
}
