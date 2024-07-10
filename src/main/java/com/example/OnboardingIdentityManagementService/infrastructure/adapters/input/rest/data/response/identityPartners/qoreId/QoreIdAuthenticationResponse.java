package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QoreIdAuthenticationResponse {

    private String accessToken;

    private Long expiresIn;

    private String tokenType;
}
