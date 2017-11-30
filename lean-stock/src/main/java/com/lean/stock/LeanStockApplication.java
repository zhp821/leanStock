package com.lean.stock;

import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.*;
import org.slf4j.*;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
public class LeanStockApplication
{
    private static final Logger LOGGER;
    
    public static void main(final String[] args) {
        SpringApplication.run((Object)LeanStockApplication.class, args);
        LeanStockApplication.LOGGER.info("LeanStockApplication start success!");
    }
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)LeanStockApplication.class);
    }
}
