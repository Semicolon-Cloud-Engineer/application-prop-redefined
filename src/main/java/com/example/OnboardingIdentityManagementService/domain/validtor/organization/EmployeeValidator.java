package com.example.OnboardingIdentityManagementService.domain.validtor.organization;

import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.IdentityManagerContext;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class EmployeeValidator {

    public static void validateInviteEmployeeSystemOwnerAccountRequest(InviteEmployeeRequest employeeRequest) throws InvalidArgumentException, KeycloakException {
        if (StringUtils.isEmpty(employeeRequest.getFirstName()) ||
                StringUtils.isEmpty(employeeRequest.getLastName()) ||
                StringUtils.isEmpty(employeeRequest.getEmail()) ||
                StringUtils.isEmpty(employeeRequest.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(employeeRequest.getEmail()) ||
                employeeRequest.getOrganization() == null
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        employeeRequest.setEmail(employeeRequest.getEmail().trim());
        employeeRequest.setFirstName(employeeRequest.getFirstName().trim());
        employeeRequest.setLastName(employeeRequest.getLastName().trim());
        if (StringUtils.isEmpty(employeeRequest.getFirstName()) ||
                StringUtils.isEmpty(employeeRequest.getLastName()) ||
                StringUtils.isEmpty(employeeRequest.getEmail()) ||
                StringUtils.isEmpty(employeeRequest.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(employeeRequest.getEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        KeycloakService keycloakService = IdentityManagerContext.getBean(KeycloakService.class);
        if (!keycloakService.doesClientExist(employeeRequest.getNameOfClientCreatedForCompany())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.EMAIL_ALREADY_EXISTS);
        }
    }

    public static void validateInviteNonSystemOwnerEmployeeAccountRequest(InviteEmployeeRequest employeeRequest) throws InvalidArgumentException, KeycloakException {
        if (StringUtils.isEmpty(employeeRequest.getFirstName()) ||
                StringUtils.isEmpty(employeeRequest.getLastName()) ||
                StringUtils.isEmpty(employeeRequest.getEmail()) ||
                StringUtils.isEmpty(employeeRequest.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(employeeRequest.getEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }


        employeeRequest.setEmail(employeeRequest.getEmail().trim());
        employeeRequest.setFirstName(employeeRequest.getFirstName().trim());
        employeeRequest.setLastName(employeeRequest.getLastName().trim());
        if (StringUtils.isEmpty(employeeRequest.getFirstName()) ||
                StringUtils.isEmpty(employeeRequest.getLastName()) ||
                StringUtils.isEmpty(employeeRequest.getEmail()) ||
                StringUtils.isEmpty(employeeRequest.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(employeeRequest.getEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        KeycloakService keycloakService = IdentityManagerContext.getBean(KeycloakService.class);
        if (!keycloakService.doesClientExist(employeeRequest.getNameOfClientCreatedForCompany())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.EMAIL_ALREADY_EXISTS);
        }
    }
}
