package com.example.social.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.getstream.services.framework.StreamSDKClient;

@Configuration
public class StreamConfig {

    @Value("${stream.api.key}")
    private String apiKey;

    @Value("${stream.api.secret}")
    private String apiSecret;

    @Bean
    public StreamSDKClient streamClient() {
        return new StreamSDKClient(apiKey, apiSecret);
    }
}