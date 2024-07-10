package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.mapper;

import com.example.OnboardingIdentityManagementService.domain.model.organization.OrganizationDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;


public class OrganizationMapper {

    public static InviteOrganizationResponse convertOrganizationDomainObjectToInviteOrganizationResponse(OrganizationDomainObject organizationDomainObject) {
        return InviteOrganizationResponse.builder()
                .id(organizationDomainObject.getOrganizationId())
                .fullName(organizationDomainObject.getBusinessParty().getFullName())
                .companyNumber(organizationDomainObject.getCompanyNumber())
                .nameOfDirector(organizationDomainObject.getNameOfDirector())
                .build();
    }

}
