package me.bis.dbshop.controller;

import me.bis.dbshop.domain.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration Test to find nearest shop
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class GeoShopFinderControllerErrorTest {

    @LocalServerPort
    private int port;

    @Test
    public void findNearestShop404Test() {
        String CURRENT_LATITUDE = "51.1424544";
        String CURRENT_LONGITUDE = "-0.0642075";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Shop> findResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/shop?latitude=" + CURRENT_LATITUDE + "&longitude=" + CURRENT_LONGITUDE, Shop.class);
        assertThat(findResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
