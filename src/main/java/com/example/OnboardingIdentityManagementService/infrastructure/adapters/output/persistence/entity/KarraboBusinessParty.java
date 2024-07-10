package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboBusinessParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long karraboBusinessPartyId;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private BusinessPartyType businessPartyType;

    private final LocalDateTime createdAt = LocalDateTime.now();

}
