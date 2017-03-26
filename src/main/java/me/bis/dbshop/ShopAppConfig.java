package me.bis.dbshop;

import me.bis.dbshop.service.ShopFindService;
import me.bis.dbshop.service.ShopFindServiceImpl;
import me.bis.dbshop.service.google.GoogleLocationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopAppConfig {

    @Bean
    public ShopFindService shopFindService() {
        return new ShopFindServiceImpl(
                new GoogleLocationServiceImpl());
    }
}
