package com.example.OnboardingIdentityManagementService.application.ports.output.organization;

import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.model.organization.OrganizationDomainObject;


public interface OrganizationOutputPort {

    OrganizationDomainObject save(OrganizationDomainObject organization);

    boolean existsByCompanyNumber(String companyNumber) throws InvalidArgumentException;
}
