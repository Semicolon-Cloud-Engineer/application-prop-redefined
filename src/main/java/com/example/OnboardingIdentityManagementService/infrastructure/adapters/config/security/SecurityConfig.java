package com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/identity/callOtherService",
                                "/api/v1/identity/callOtherService/{message}",
                                "/api/v1/identity/confirm-mail",
                                "/api/v1/identity/invite-organization"
                                )
                        .permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/documentation/**",
                                "/documentation/v3/api-docs/swagger-config",
                                "/documentation/v3/api-docs/swagger-config/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/documentation/v3/api-docs",
                                "/actuator/**",
                                "/reports",
                                "/report",
                                "/reports/**",
                                "/report/**"
                        )
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(t -> t.jwt(Customizer.withDefaults()))
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

}
