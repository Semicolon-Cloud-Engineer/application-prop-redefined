package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QoreIdBvnResponse {

    private QoreIdApplicantData applicant;

    private QoreIdBvnData bvn;

    private QoreStatus status;

    private QoreIdSummary summary;
}
