package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence;

import com.example.OnboardingIdentityManagementService.application.ports.output.organization.OrganizationOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.model.organization.OrganizationDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationPersistenceAdapter implements OrganizationOutputPort {

    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationDomainObject save(OrganizationDomainObject organization) {
        KarraboOrganization karraboOrganization = organization.toOrganizationEntity();
        karraboOrganization = organizationRepository.save(karraboOrganization);
        return new OrganizationDomainObject(karraboOrganization);
    }

    @Override
    public boolean existsByCompanyNumber(String companyNumber) throws InvalidArgumentException {
        if (StringUtils.isEmpty(companyNumber)) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_COMPANY_NUMBER);
        }
        return organizationRepository.existsByCompanyNumber(companyNumber);
    }

}
