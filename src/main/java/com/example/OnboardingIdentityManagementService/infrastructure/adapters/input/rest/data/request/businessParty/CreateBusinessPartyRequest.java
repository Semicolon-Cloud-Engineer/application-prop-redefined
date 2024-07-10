package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty;


import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateBusinessPartyRequest {

    private String fullName;

    private BusinessPartyType businessPartyType;

}
