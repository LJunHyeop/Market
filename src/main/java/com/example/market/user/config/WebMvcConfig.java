package com.example.market.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;


import java.io.IOException;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfig(@Value("${file.directory}") String uploadPath) {
        log.info("file.directory: {}", uploadPath);
        this.uploadPath = uploadPath;
    }


    @Override // CORS 오픈
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(false) // 쿠키 요청을 허용
                .maxAge(3600) ;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath);

        registry.addResourceHandler("/**") //를
                .addResourceLocations("classpath:/static/**")//로 맵핑
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        log.info("resourcePath: {}", resourcePath);
                        Resource requestedResource = location.createRelative(resourcePath);

                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}