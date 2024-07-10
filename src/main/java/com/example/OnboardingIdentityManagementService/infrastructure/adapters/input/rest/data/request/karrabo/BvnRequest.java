package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BvnRequest {

    private String firstName;
    private String lastName;
    private String bankVerificationNumber;
    private String phoneNumber;
}
