package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity;


import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.AffiliateData;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.CompanyStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessIdentityResponse {

    private String companyType;

    private String cacCheck;

    private String cacStatus;

    private String cacState;

    private String headOfficeAddress;

    private CompanyStatus companyStatus;

    private String companyEmail;

    private String rcNumber;

    private String classification;

    private String companyName;

    private List<AffiliateData> affiliates;
}
