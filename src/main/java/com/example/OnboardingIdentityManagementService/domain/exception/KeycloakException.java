package com.example.OnboardingIdentityManagementService.domain.exception;

public class KeycloakException extends KarraboException {

    public KeycloakException(String message) {
        super(message);
    }

    public KeycloakException(Throwable cause) {
        super(cause);
    }

}
