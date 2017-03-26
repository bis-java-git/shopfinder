package me.bis.dbshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring boot application
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class ShopApp {

    private static final Logger logger = LoggerFactory.getLogger(ShopApp.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ShopApp.class, args);
    }
}
