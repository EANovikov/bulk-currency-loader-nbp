package com.xevgnov.bulkcurrencyrateuploader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@EnableRetry
public class WebClientConfig {

    @Value("${nbp.api.url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        log.info("Base URL is: {}", baseUrl);
        return WebClient.builder().baseUrl(baseUrl).build();
    }

}
