package com.example.OnboardingIdentityManagementService.domain.model.businessParty;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessPartyDomainObject {

    private String karraboBusinessPartyId;

    private String fullName;

    private BusinessPartyType businessPartyType;

    private LocalDateTime createdAt;

    public BusinessPartyDomainObject(KarraboBusinessParty businessParty) {
        this.karraboBusinessPartyId = businessParty.getKarraboBusinessPartyId();
        this.fullName = businessParty.getFullName();
        this.businessPartyType = businessParty.getBusinessPartyType();
        this.createdAt = businessParty.getCreatedAt();
    }

    public BusinessPartyDomainObject(CreateBusinessPartyRequest businessPartyRequest) {
        this.fullName = businessPartyRequest.getFullName();
        this.businessPartyType = businessPartyRequest.getBusinessPartyType();
    }

    public KarraboBusinessParty toBusinessPartyEntity() {
        return KarraboBusinessParty.builder()
                .businessPartyType(businessPartyType)
                .fullName(fullName)
                .karraboBusinessPartyId(karraboBusinessPartyId)
                .build();
    }
}
