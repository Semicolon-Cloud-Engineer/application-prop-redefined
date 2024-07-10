package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;


import com.example.OnboardingIdentityManagementService.domain.model.enums.OperationStatus;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.KarraboOrganizationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboOrganization {

    @Id
    private String organizationId;

    @OneToOne
    private KarraboBusinessParty businessParty;

    private String companyNumber;

    private String dateOfRegistration;

    private String businessType;

    private String headOfficeAddress;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;

    private String nameOfDirector;

    @Enumerated(EnumType.STRING)
    private KarraboOrganizationType organizationType;

}
