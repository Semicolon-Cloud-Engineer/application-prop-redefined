package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboBusinessParty {

    @Id
    private String karraboBusinessPartyId;

    private String fullName;

    private BusinessPartyType businessPartyType;

    private final LocalDateTime createdAt = LocalDateTime.now();

}
