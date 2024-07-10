package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId;

import com.example.OnboardingIdentityManagementService.domain.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NinRequest {

    private String nin;
    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private String phonenumber;
    private Gender gender;
    private LocalDate dateOfBirth;
}
