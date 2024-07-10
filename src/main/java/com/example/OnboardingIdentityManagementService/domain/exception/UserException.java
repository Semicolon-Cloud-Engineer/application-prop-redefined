package com.example.OnboardingIdentityManagementService.domain.exception;

public class UserException extends KarraboException {


    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
