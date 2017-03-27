package me.bis.dbshop.acceptancetest;

import ch.qos.logback.classic.Logger;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import me.bis.dbshop.ShopApp;
import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.ShopAddress;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Acceptance Step definition for finding shop based on current latitude and longitude
 * executing commands from gherkin language feature file
 */
@ContextConfiguration(classes = ShopApp.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ShopFinderStepDefinition {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(ShopFinderStepDefinition.class);
    @LocalServerPort
    private int port;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    /**
     * Part of saving data between definition
     * Please note this should be part of caching service
     */
    private ResponseEntity<Shop> shopResponseEntity;

    private Shop createShop(String shopName,
                            Integer shopNumber,
                            String shopPostCode) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddress(new ShopAddress(shopNumber, shopPostCode));
        return shop;
    }

    @Test
    @When("^the shop Manager adds  /shop method$")
    public void the_shop_Manager_adds_shop_POST_method(DataTable dataTable) throws Throwable {
        List<List<String>> shopDataList = dataTable.raw();
        for (List<String> addShopData : shopDataList) {
            Shop shop = createShop(addShopData.get(0), Integer.parseInt(addShopData.get(1)), addShopData.get(2));
            ResponseEntity<Shop> savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
            assertThat(savedShop, is(notNullValue()));
        }
    }

    @Test
    @When("^the shop Manager finds /shop/find method$")
    public void the_shop_Manager_finds_shop_find_POST_method(DataTable dataTable) throws Throwable {
        List<List<String>> shopFindParameters = dataTable.raw();
        shopResponseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/shop?latitude=" + shopFindParameters.get(0).get(0) + "&longitude=" + shopFindParameters.get(0).get(1), Shop.class);
        assertThat(shopResponseEntity, is(notNullValue()));
        logger.info("/shop {} ", shopResponseEntity.getBody());
    }

    @Test
    @Then("^it receives nearest shop$")
    public void it_receives_nearest_shop() throws Throwable {
        String EXPECTED_CR0_SHOP_NAME = "BIS Croydon";
        assertThat(shopResponseEntity, is(notNullValue()));
        assertThat(shopResponseEntity.getBody().getShopName(), is(EXPECTED_CR0_SHOP_NAME));
    }
}
