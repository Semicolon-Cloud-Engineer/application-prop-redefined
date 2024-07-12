package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InviteEmployeeRequest {

    @NotBlank(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    @NotNull(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    private String firstName;

    @NotBlank(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    @NotNull(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    private String lastName;

    @NotBlank(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    @NotNull(message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    @Email(message = ExceptionMessageConstants.INVALID_EMAIL_ADDRESS)
    private String email;

    @NotBlank(message = ExceptionMessageConstants.INVALID_CLIENT_NAME)
    @NotNull(message = ExceptionMessageConstants.INVALID_CLIENT_NAME)
    private String nameOfClientCreatedForCompany;

    private KarraboOrganization organization;

    private CreateBusinessPartyRequest businessPartyRequest;

}
