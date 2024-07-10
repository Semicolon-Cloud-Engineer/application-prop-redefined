package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import com.example.OnboardingIdentityManagementService.domain.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BvnResponse {

    private String bvn;

    private String firstName;

    private String lastName;

    private String middleName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String photo;

    private String residentialLga;

    private String maritalStatus;

    private String nationality;

    private String residentialAddress;

    private String stateOfResidence;

    private String email;

    private String enrollmentBank;

    private QoreIdBvnWatchListed watchListed;
}
