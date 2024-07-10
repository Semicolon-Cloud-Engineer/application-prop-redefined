package com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.pulsar;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

@Component
public class PulsarComponent {

    @Autowired
    private PulsarTemplate<String> stringTemplate;

    private static final String STRING_TOPIC = "string-topic";

    public void sendStringMessageToPulsarTopic(String str) throws PulsarClientException {
        stringTemplate.send(STRING_TOPIC, str);
    }

    public void sendNotificationEmail() {

    }
}
