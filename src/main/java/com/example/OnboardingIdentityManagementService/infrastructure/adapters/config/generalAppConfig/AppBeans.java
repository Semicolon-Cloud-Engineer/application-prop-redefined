package com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.generalAppConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppBeans {

    @Bean
    public RestClient restClient() {
        RestTemplate oldRestTemplate = new RestTemplate();
        return  RestClient.create(oldRestTemplate);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
