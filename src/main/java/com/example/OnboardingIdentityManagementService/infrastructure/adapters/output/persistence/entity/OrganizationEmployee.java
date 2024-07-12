package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class OrganizationEmployee {

    @Id
    private String id;

    private KarraboOrganization organization;

    private KarraboPlatformUser employee;

}
