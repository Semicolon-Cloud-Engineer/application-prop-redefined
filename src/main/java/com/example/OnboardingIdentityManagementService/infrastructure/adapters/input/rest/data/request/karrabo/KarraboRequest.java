package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KarraboRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDate dateOfBirth;

}
