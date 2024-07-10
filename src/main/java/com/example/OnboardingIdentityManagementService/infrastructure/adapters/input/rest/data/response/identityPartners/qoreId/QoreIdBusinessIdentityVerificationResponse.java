package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QoreIdBusinessIdentityVerificationResponse {

    private MetaData metadata;

    private Summary summary;

    private QoreStatus status;

    private QoreCac cac;

}
