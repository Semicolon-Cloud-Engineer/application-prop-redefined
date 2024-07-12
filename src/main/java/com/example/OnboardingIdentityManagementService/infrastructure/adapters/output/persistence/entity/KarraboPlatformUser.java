package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KarraboPlatformUser {

    @Id
    private String userId;

    private KarraboBusinessParty businessParty;

    private String firstName;

    private String lastName;

    private String email;

}
