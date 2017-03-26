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

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ShopControllerErrorTest {

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private BigDecimal CURRENT_LATITUDE = new BigDecimal("51.1424544");

    private BigDecimal CURRENT_LONGITUDE = new BigDecimal("-0.0642075");

    @Test
    public void findNearestShop404Test() {
        ResponseEntity<Shop> findResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/shop?latitude=" + "51.1424544" + "&longitude=" + "-0.0642075", Shop.class);
        assertThat(findResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
