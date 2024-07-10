package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteOrganizationResponse {

    private String id;

    private String fullName;

    private String companyNumber;

    private String nameOfDirector;

    private InviteEmployeeResponse employeeData;


}
