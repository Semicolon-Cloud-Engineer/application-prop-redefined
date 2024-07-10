package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QoreIdAuthenticationRequest {

    private String clientId;

    private String secret;
}
