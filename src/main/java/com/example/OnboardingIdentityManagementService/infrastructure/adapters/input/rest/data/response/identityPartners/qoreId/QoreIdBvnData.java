package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QoreIdBvnData {

    private String bvn;
    private String firstname;
    private String lastname;
    private String middlename;
    private String birthdate;
    private String gender;
    private String photo;
    private String lga_of_residence;
    private String marital_status;
    private String nationality;
    private String residential_address;
    private String state_of_residence;
    private String email;
    private String enrollment_bank;
    private QoreIdBvnWatchListed watch_listed;

}
