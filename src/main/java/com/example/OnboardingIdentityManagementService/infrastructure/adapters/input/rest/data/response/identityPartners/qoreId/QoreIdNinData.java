package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class QoreIdNinData {

    private String nin;
    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private String phone;
    private String gender;
    private String photo;
    private String birthdate;
    private QoreIdNinAddress residence;
}
