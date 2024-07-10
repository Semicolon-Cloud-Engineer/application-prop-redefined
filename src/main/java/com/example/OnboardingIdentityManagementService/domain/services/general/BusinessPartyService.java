package com.example.OnboardingIdentityManagementService.domain.services.general;

import com.example.OnboardingIdentityManagementService.application.ports.input.CreateBusinessPartyUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.businessParty.BusinessPartyOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.BusinessPartyException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.domain.validtor.general.BusinessPartyValidator;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessPartyService implements CreateBusinessPartyUseCase {

    private final BusinessPartyOutputPort businessPartyOutputPort;

    @Override
    public BusinessPartyDomainObject createBusinessParty(CreateBusinessPartyRequest businessPartyRequest) throws BusinessPartyException {
        try {
            BusinessPartyValidator.validateCreateBusinessPartyRequest(businessPartyRequest);
            BusinessPartyDomainObject businessPartyDomainObject = new BusinessPartyDomainObject(businessPartyRequest);
            businessPartyDomainObject = businessPartyOutputPort.save(businessPartyDomainObject);
//            return BusinessPartyMapper.convertBusinessPartyDomainObjectRoBusinessPartyResponse(businessPartyDomainObject);
            return businessPartyDomainObject;
        } catch (InvalidArgumentException e) {
            throw new BusinessPartyException(e);
        }
    }

}
