package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboPlatformUser {

    @Id
    private String userId;

    @OneToOne
    private KarraboBusinessParty businessParty;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

}
