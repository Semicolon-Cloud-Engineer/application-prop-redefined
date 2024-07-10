package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QoreIdSummary {

    private QoreIdCheck nin_check;

    private QoreIdCheck bvn_check;
}
