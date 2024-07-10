package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class QoreIdNinResponse {

    private QoreIdApplicantData applicant;

    private QoreIdNinData nin;

    private QoreStatus status;

    private QoreIdSummary summary;

}
