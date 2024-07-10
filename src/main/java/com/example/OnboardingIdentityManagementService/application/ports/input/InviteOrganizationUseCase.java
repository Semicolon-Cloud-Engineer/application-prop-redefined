package com.example.OnboardingIdentityManagementService.application.ports.input;


import com.example.OnboardingIdentityManagementService.domain.exception.OrganizationException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;

public interface InviteOrganizationUseCase {

    InviteOrganizationResponse inviteOrganization(InviteOrganizationRequest merchantAcquirerRequest) throws OrganizationException;

}
