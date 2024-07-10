package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QoreCac {

    private String state;

    private String headOfficeAddress;

    private String city;

    private CompanyStatus status;

    private String companyEmail;

    private String rcNumber;

    private String classification;

    private String companyName;

    private List<AffiliateData> affiliatesData;

}
