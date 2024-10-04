package com.example.market;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing // auditing ??? ????
@OpenAPIDefinition(info = @Info(title = "API 명", version = "v1"))
public class MarketApplication {

    public static void main(String[] args) {

        SpringApplication.run(MarketApplication.class, args);
    }

}
