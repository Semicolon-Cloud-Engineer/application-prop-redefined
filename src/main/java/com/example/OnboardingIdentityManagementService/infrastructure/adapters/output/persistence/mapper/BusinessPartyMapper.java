package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.mapper;

import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.businessParty.CreateBusinessPartyResponse;

public class BusinessPartyMapper {

    public static CreateBusinessPartyResponse convertBusinessPartyDomainObjectRoBusinessPartyResponse(BusinessPartyDomainObject businessPartyDomainObject) {
        return CreateBusinessPartyResponse.builder()
                .id(businessPartyDomainObject.getKarraboBusinessPartyId())
                .fullName(businessPartyDomainObject.getFullName())
                .build();
    }

}
