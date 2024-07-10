package com.example.OnboardingIdentityManagementService.infrastructure.adapters.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class IdentityManagerContext {

    private static IdentityManagerContext instance;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    public static <T> T getBean(Class<T> requiredBean) {
        return instance.applicationContext.getBean(requiredBean);
    }


}
