package com.example.OnboardingIdentityManagementService.application.ports.output.identityPartners;

import com.example.OnboardingIdentityManagementService.domain.exception.IdentityException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.CacRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;

public interface BusinessIdentityServiceUseCase {

    BusinessIdentityResponse verifyCompanyCacNumber(CacRequest request) throws IdentityException;
}
