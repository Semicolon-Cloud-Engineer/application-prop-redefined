package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResidentialAddress {

    private String country;
    private String state;
    private String lga;
    private String city;
    private String address;
    private String streetNumber;
    private String postcode;
}
