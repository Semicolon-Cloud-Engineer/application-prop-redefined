package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.businessParty;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBusinessPartyResponse {

    private String id;

    private String fullName;

}
