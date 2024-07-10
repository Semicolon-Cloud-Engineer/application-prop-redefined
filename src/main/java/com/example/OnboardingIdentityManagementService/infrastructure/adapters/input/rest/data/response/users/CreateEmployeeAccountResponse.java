package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeAccountResponse {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private KarraboPlatformUser employeeUser;

}
