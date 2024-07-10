package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateData {

    private String surname;

    private String firstname;

    private String othername;

    private String email;
    private String phoneNumber;
    private String gender;
    private String nationality;
    private String streetNumber;
    private String identityNumber;
    private String idType;
    private String occupation;
    private String city;
    private CompanyStatus status;
    private String address;
    private boolean isChairman;
    private boolean isDesignated;
    private AffiliateType affiliateType;
    private AffiliateType affiliateCountry;
    private ResidentialAddress affiliatesResidentialAddress;
}
