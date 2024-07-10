package com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class KeycloakConfig {

    private final KeycloakConfigProperties keycloakConfigProperties;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.master-realm}")
    private String masterRealm;



    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .realm(masterRealm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(keycloakConfigProperties.getUsername())
                .password(keycloakConfigProperties.getPassword())
                .serverUrl(serverUrl)
                .build();

    }
}
