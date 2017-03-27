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
        String TW6_SHOP_POSTCODE = "TW6";
        String TW6_SHOP_NAME = "BIS Heathrow";
        Integer TW6_SHOP_NUMBER = 3;
        Shop shop = createShop(TW6_SHOP_NAME, TW6_SHOP_NUMBER, TW6_SHOP_POSTCODE);
        ResponseEntity<Shop> savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
        assertThat(savedShop.getBody(), is(nullValue()));

        savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
        assertThat(savedShop.getBody(), is(notNullValue()));
        assertThat(savedShop.getBody().getShopName(), is(TW6_SHOP_NAME));
    }

    @Test
    public void findNearestShopTest() {
        String SW17_SHOP_NAME = "BIS Balham";
        String SW17_SHOP_POSTCODE = "SW17";
        Integer SW17_SHOP_NUMBER = 1;
        Shop sw17Shop = createShop(SW17_SHOP_NAME, SW17_SHOP_NUMBER, SW17_SHOP_POSTCODE);
        ResponseEntity<Shop> response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", sw17Shop, Shop.class);
        assertThat(response.getBody(), is(nullValue()));

        String CR0_SHOP_NAME = "BIS Croydon";
        Integer CR0_SHOP_NUMBER = 2;
        String CR0_SHOP_POSTCODE = "CR0";
        Shop cr0Shop = createShop(CR0_SHOP_NAME, CR0_SHOP_NUMBER, CR0_SHOP_POSTCODE);
        response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", cr0Shop, Shop.class);
        assertThat(response.getBody(), is(nullValue()));

        String TW6_SHOP_POSTCODE = "TW6";
        Integer TW6_SHOP_NUMBER = 3;
        String TW6_SHOP_NAME = "BIS Heathrow";
        Shop tw6Shop = createShop(TW6_SHOP_NAME, TW6_SHOP_NUMBER, TW6_SHOP_POSTCODE);
        response = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", tw6Shop, Shop.class);
        assertThat(response.getBody(), is(notNullValue()));

        String CURRENT_LATITUDE = "51.1424544";
        String CURRENT_LONGITUDE = "-0.0642075";
        ResponseEntity<Shop> findResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/shop/?latitude=" + CURRENT_LATITUDE + "&longitude=" + CURRENT_LONGITUDE, Shop.class);

        assertThat(findResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(findResponse.getBody().getShopName(), is(CR0_SHOP_NAME));
        assertThat(findResponse.getBody().getShopAddress().getNumber(), is(CR0_SHOP_NUMBER));
        assertThat(findResponse.getBody().getShopAddress().getPostCode(), is(CR0_SHOP_POSTCODE));
    }
}
