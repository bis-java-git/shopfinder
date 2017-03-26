package me.bis.dbshop.controller;


import ch.qos.logback.classic.Logger;
import me.bis.dbshop.domain.Shop;
import me.bis.dbshop.domain.net.ShopAddRequest;
import me.bis.dbshop.service.ShopFindService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Controller
public class ShopController {

    final static Logger logger = (Logger) LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopFindService shopFindService;

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    @ResponseBody
    public Shop addOrReplaceNewShop(@RequestBody ShopAddRequest shopAddRequest) {
        logger.info("Add or Replace Shop address is called.");
        Shop shop = new Shop();
        shop.setShopName(shopAddRequest.getShopName());
        shop.setShopAddress(shopAddRequest.getShopAddress());
        Shop addShop = shopFindService.addShop(shop);
        logger.info("Shop added is {} ", shop);
        return addShop;
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    @ResponseBody
    public Shop shopFindRequest(@RequestParam String latitude,
                                @RequestParam String longitude,
                                HttpServletResponse response
    ) {
        logger.info("Find or Replace Shop address is called.");

        try {
            return shopFindService.findNearestShop(new BigDecimal(latitude), new BigDecimal(longitude));
        } catch (NoSuchElementException exc) {
            logger.error("No shop data present latitude {} longitude {}", latitude, longitude);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }
}
