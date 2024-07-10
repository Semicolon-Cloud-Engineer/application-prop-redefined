package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QoreIdNinRequest {

    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String gender;
    private LocalDate dob;

}
