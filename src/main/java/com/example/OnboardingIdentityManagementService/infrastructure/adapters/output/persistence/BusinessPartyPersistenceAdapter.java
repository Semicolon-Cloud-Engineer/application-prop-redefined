package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence;

import com.example.OnboardingIdentityManagementService.application.ports.output.businessParty.BusinessPartyOutputPort;
import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.BusinessPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessPartyPersistenceAdapter implements BusinessPartyOutputPort {

    private final BusinessPartyRepository businessPartyRepository;

    @Override
    public BusinessPartyDomainObject save(BusinessPartyDomainObject businessPartyDomainObject) {
        KarraboBusinessParty businessParty = businessPartyDomainObject.toBusinessPartyEntity();
        businessParty = businessPartyRepository.save(businessParty);
        return new BusinessPartyDomainObject(businessParty);
    }

}
