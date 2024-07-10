package com.example.OnboardingIdentityManagementService.application.ports.output.businessParty;

import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;

public interface BusinessPartyOutputPort {

    BusinessPartyDomainObject save(BusinessPartyDomainObject businessPartyDomainObject);
}
