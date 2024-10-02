package com.example.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing // auditing 기능 활성화
public class MarketApplication {

    public static void main(String[] args) {
        System.out.println("DEFAULT_DB_URL: " + System.getenv("DEFAULT_DB_URL"));
        SpringApplication.run(MarketApplication.class, args);
    }

}
