package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;


import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QoreIdNinAddress {

    private String address1;
    private String lga;
    private String state;
}
