package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QoreIdCheck {

    private QoreIdiNinCheckStatus status;

    private QoreIdFieldMatches fieldMatches;

}
