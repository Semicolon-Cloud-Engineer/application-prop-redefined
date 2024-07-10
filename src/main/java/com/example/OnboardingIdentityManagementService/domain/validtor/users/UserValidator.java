package com.example.OnboardingIdentityManagementService.domain.validtor.users;

import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.IdentityManagerContext;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.utils.constants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class UserValidator {


    public static void validateInviteEmployeeRequest(InviteEmployeeRequest request) throws InvalidArgumentException, KeycloakException {
        if (StringUtils.isEmpty(request.getFirstName()) ||
                StringUtils.isEmpty(request.getLastName()) ||
                StringUtils.isEmpty(request.getEmail()) ||
                StringUtils.isEmpty(request.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(request.getEmail().trim())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        request.setFirstName(request.getFirstName().trim());
        request.setLastName(request.getLastName().trim());
        request.setNameOfClientCreatedForCompany(request.getNameOfClientCreatedForCompany().trim());
        request.setEmail(request.getEmail().trim());
        if (StringUtils.isEmpty(request.getFirstName()) ||
                StringUtils.isEmpty(request.getLastName()) ||
                StringUtils.isEmpty(request.getEmail()) ||
                StringUtils.isEmpty(request.getNameOfClientCreatedForCompany()) ||
                !EmailValidator.getInstance().isValid(request.getEmail().trim())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        KeycloakService keycloakService = IdentityManagerContext.getBean(KeycloakService.class);
        if (keycloakService.doesEmailExist(request.getEmail())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.EMAIL_ALREADY_EXISTS);
        }
        if (!keycloakService.doesClientExist(request.getNameOfClientCreatedForCompany())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.ORGANIZATION_CLIENT_NOT_FOUND);
        }
    }


    public static void validateUserRequestDto(KarraboRequest userRequestDto) throws InvalidArgumentException, KeycloakException {
        if (StringUtils.isEmpty(userRequestDto.getFirstName()) ||
                StringUtils.isEmpty(userRequestDto.getLastName()) ||
                StringUtils.isEmpty(userRequestDto.getEmail()) ||
                !EmailValidator.getInstance().isValid(userRequestDto.getEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        userRequestDto.setFirstName(userRequestDto.getFirstName().trim());
        userRequestDto.setLastName(userRequestDto.getLastName().trim());
        userRequestDto.setEmail(userRequestDto.getEmail().trim());
        if (StringUtils.isEmpty(userRequestDto.getFirstName()) ||
                StringUtils.isEmpty(userRequestDto.getLastName()) ||
                StringUtils.isEmpty(userRequestDto.getEmail()) ||
                !EmailValidator.getInstance().isValid(userRequestDto.getEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        KeycloakService keycloakService = IdentityManagerContext.getBean(KeycloakService.class);
        if (keycloakService.doesEmailExist(userRequestDto.getEmail())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.EMAIL_ALREADY_EXISTS);
        }
    }
}
