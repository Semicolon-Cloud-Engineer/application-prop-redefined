package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteEmployeeResponse {

    private String id;

    private String firstName;

    private String lastName;

}
