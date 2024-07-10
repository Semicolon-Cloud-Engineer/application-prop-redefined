package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CacRequest extends KarraboRequest {

    private String companyNumber;

}
