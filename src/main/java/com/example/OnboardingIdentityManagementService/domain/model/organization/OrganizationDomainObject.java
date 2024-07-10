package com.example.OnboardingIdentityManagementService.domain.model.organization;

import com.example.OnboardingIdentityManagementService.domain.model.enums.OperationStatus;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.KarraboOrganizationType;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDomainObject {

    private String organizationId;

    private KarraboBusinessParty businessParty;

    private String companyNumber;

    private String dateOfRegistration;

    private String businessType;

    private String headOfficeAddress;

    private OperationStatus operationStatus;

    private String nameOfDirector;

    private KarraboOrganizationType organizationType;

    public OrganizationDomainObject(BusinessIdentityResponse businessIdentityResponse) {
        this.companyNumber = businessIdentityResponse.getRcNumber().trim();
        this.headOfficeAddress = businessIdentityResponse.getHeadOfficeAddress();
        this.operationStatus = OperationStatus.valueOf(businessIdentityResponse.getCompanyStatus().name());
        // TODO: Get director details from here
        //  for (AffiliateData data: businessIdentityResponse.getAffiliates()) {}
    }

    public OrganizationDomainObject(KarraboOrganization organization) {
        this.organizationId = organization.getOrganizationId();
        this.businessParty = organization.getBusinessParty();
        this.companyNumber = organization.getCompanyNumber();
        this.dateOfRegistration = organization.getDateOfRegistration();
        this.businessType = organization.getBusinessType();
        this.headOfficeAddress = organization.getHeadOfficeAddress();
        this.operationStatus = organization.getOperationStatus();
        this.nameOfDirector = organization.getNameOfDirector();
        this.organizationType = organization.getOrganizationType();
    }

    public KarraboOrganization toOrganizationEntity() {
        return KarraboOrganization.builder()
                .organizationId(organizationId)
                .businessParty(businessParty)
                .companyNumber(companyNumber)
                .dateOfRegistration(dateOfRegistration)
                .businessType(businessType)
                .headOfficeAddress(headOfficeAddress)
                .operationStatus(operationStatus)
                .nameOfDirector(nameOfDirector)
                .organizationType(organizationType)
                .build();
    }

}
