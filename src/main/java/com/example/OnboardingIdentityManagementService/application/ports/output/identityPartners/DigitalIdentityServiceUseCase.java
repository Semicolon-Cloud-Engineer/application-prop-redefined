package com.example.OnboardingIdentityManagementService.application.ports.output.identityPartners;


import com.example.OnboardingIdentityManagementService.domain.exception.IdentityException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.BvnRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId.NinRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.BvnResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.NinResponse;

public interface DigitalIdentityServiceUseCase {

    NinResponse verifyNin(NinRequest identityRequest) throws InvalidArgumentException, IdentityException;

    BvnResponse verifyBvn(BvnRequest bvnRequest) throws IdentityException, InvalidArgumentException;
}
