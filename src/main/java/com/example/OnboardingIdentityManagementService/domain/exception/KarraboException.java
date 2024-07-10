package com.example.OnboardingIdentityManagementService.domain.exception;

public class KarraboException extends Exception {

    public KarraboException(String message) {
        super(message);
    }

    public KarraboException(String message, Throwable cause) {
        super(message, cause);
    }

    public KarraboException(Throwable cause) {
        super(cause);
    }

}
