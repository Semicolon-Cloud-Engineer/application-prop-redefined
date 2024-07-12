package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InviteOrganizationRequest {

    @NotBlank(message = ExceptionMessageConstants.INVALID_COMPANY_NUMBER)
    @NotNull(message = ExceptionMessageConstants.INVALID_COMPANY_NUMBER)
    @Length(min = 8, message = ExceptionMessageConstants.INVALID_COMPANY_NUMBER)
    private String companyNumber;

    @NotBlank(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_FIRST_NAME)
    @NotNull(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_FIRST_NAME)
    @Length(min = 3, message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    private String contactPersonFirstName;

    @NotBlank(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_LAST_NAME)
    @NotNull(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_LAST_NAME)
    @Length(min = 3, message = ExceptionMessageConstants.INVALID_NAME_INPUT)
    private String contactPersonLastName;

    @NotBlank(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_EMAIL_ADDRESS)
    @NotNull(message = ExceptionMessageConstants.EMPTY_CONTACT_PERSON_EMAIL_ADDRESS)
    @Email(message = ExceptionMessageConstants.INVALID_EMAIL_ADDRESS)
    private String contactPersonEmail;

    @NotNull(message = ExceptionMessageConstants.INVALID_ORGANIZATION_TYPE)
    private OrganizationType organizationType;

}
