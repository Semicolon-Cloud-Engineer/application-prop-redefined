package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import com.example.OnboardingIdentityManagementService.domain.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NinResponse {

    private String nin;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
}
