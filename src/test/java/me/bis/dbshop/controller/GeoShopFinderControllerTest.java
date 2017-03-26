package me.bis.dbshop.controller;

import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.ShopAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration test for adding or replacing shop address data
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class GeoShopFinderControllerTest {

    @LocalServerPort
    private int port;

    private static String SW17_SHOP_NAME = "BIS Balham";

    private static String CR0_SHOP_NAME = "BIS Croydon";

    private static String TW6_SHOP_NAME = "BIS Heathrow";

    private static Integer SW17_SHOP_NUMBER = 1;

    private static Integer CR0_SHOP_NUMBER = 2;

    private static Integer TW6_SHOP_NUMBER = 3;

    private static String SW17_SHOP_POSTCODE = "SW17";

    private static String CR0_SHOP_POSTCODE = "CR0";

    private static String TW6_SHOP_POSTCODE = "TW6";

    private static String CURRENT_LATITUDE = "51.1424544";

    private static String CURRENT_LONGITUDE = "-0.0642075";

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    private Shop createShop(String shopName,
                            Integer shopNumber,
                            String shopPostCode) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddress(new ShopAddress(shopNumber, shopPostCode));
        return shop;
    }

    @Test
    public void addShopTest() throws Exception {
        Shop shop = createShop(TW6_SHOP_NAME, TW6_SHOP_NUMBER, TW6_SHOP_POSTCODE);
        ResponseEntity<Shop> savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
        assertThat(savedShop.getBody(), is(nullValue()));

        savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
        assertThat(savedShop.getBody(), is(notNullValue()));
        assertThat(savedShop.getBody().getShopName(), is(TW6_SHOP_NAME));
    }

    @Test
    public void findNearestShopTest() {
        Shop sw17Shop = createShop(SW17_SHOP_NAME, SW17_SHOP_NUMBER, SW17_SHOP_POSTCODE);
        ResponseEntity<Shop> response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", sw17Shop, Shop.class);
        assertThat(response.getBody(), is(nullValue()));

        Shop cr0Shop = createShop(CR0_SHOP_NAME, CR0_SHOP_NUMBER, CR0_SHOP_POSTCODE);
        response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", cr0Shop, Shop.class);
        assertThat(response.getBody(), is(nullValue()));

        Shop tw6Shop = createShop(TW6_SHOP_NAME, TW6_SHOP_NUMBER, TW6_SHOP_POSTCODE);
        response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", tw6Shop, Shop.class);
        assertThat(response.getBody(), is(notNullValue()));

        ResponseEntity<Shop> findResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/shop/?latitude=" + CURRENT_LATITUDE + "&longitude=" + CURRENT_LONGITUDE, Shop.class);

        assertThat(findResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(findResponse.getBody().getShopName(), is(CR0_SHOP_NAME));
        assertThat(findResponse.getBody().getShopAddress().getNumber(), is(CR0_SHOP_NUMBER));
        assertThat(findResponse.getBody().getShopAddress().getPostCode(), is(CR0_SHOP_POSTCODE));
    }
}
