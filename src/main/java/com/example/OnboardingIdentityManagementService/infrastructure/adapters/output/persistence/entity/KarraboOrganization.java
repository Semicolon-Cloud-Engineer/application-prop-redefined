package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;


import com.example.OnboardingIdentityManagementService.domain.model.enums.OperationStatus;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.KarraboOrganizationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboOrganization {

    @Id
    private String organizationId;

    private KarraboBusinessParty businessParty;

    private String companyNumber;

    private String dateOfRegistration;

    private String businessType;

    private String headOfficeAddress;

    private OperationStatus operationStatus;

    private String nameOfDirector;

    private KarraboOrganizationType organizationType;

}
