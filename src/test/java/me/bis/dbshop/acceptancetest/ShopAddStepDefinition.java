package me.bis.dbshop.acceptancetest;

import ch.qos.logback.classic.Logger;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import me.bis.dbshop.ShopApp;
import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.ShopAddress;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(classes = ShopApp.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ShopAddStepDefinition {

    final static Logger logger = (Logger) LoggerFactory.getLogger(ShopAddStepDefinition.class);

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private ResponseEntity<Shop> savedShop;

    private Shop createShop(String shopName,
                            Integer shopNumber,
                            String shopPostCode) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddress(new ShopAddress(shopNumber, shopPostCode));
        return shop;
    }

    @When("^the shop Manager adds /shop POST method$")
    public void the_shop_Manager_calls_shop_POST_method(DataTable dataTable) throws Throwable {
        List<List<String>> list = dataTable.raw();
        Shop shop = createShop(list.get(0).get(0), Integer.parseInt(list.get(0).get(1)), list.get(0).get(2));
        savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
    }

    @Then("^it receives OK status code$")
    public void it_receives_OK_status_code() throws Throwable {
        assertThat(savedShop.getStatusCode(), is(HttpStatus.OK));
    }

    @Then("^it receives no shop details as its a new shop being added$")
    public void it_receives_no_shop_details_as_its_a_new_shop_being_added() throws Throwable {
        assertThat(savedShop.getBody(), is(nullValue()));
    }

    @When("^the shop Manager adds again /shop POST method$")
    public void the_shop_Manager_calls_again_shop_POST_method(DataTable dataTable) throws Throwable {
        List<List<String>> shopDataList = dataTable.raw();
        for (List<String> addShopData : shopDataList) {
            Shop shop = createShop(addShopData.get(0), Integer.parseInt(addShopData.get(1)), addShopData.get(2));
            savedShop = testRestTemplate.postForEntity("http://localhost:" + port + "/shop", shop, Shop.class);
        }
    }

    @Then("^it receives old shop details as its a new shop being added$")
    public void it_receives_old_shop_details_as_its_a_new_shop_being_added() throws Throwable {
        assertThat(savedShop.getBody(), is(notNullValue()));
    }
}