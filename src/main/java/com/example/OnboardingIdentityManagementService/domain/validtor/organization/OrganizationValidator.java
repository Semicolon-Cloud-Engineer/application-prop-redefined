package com.example.OnboardingIdentityManagementService.domain.validtor.organization;

import com.example.OnboardingIdentityManagementService.application.ports.output.organization.OrganizationOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.IdentityManagerContext;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class OrganizationValidator {

    public static void validateOrganizationInviteRequest(InviteOrganizationRequest request) throws InvalidArgumentException, KeycloakException {
        if (StringUtils.isEmpty(request.getCompanyNumber()) ||
                StringUtils.isEmpty(request.getContactPersonFirstName()) ||
                StringUtils.isEmpty(request.getContactPersonLastName()) ||
                !EmailValidator.getInstance().isValid(request.getContactPersonEmail()) ||
                request.getOrganizationType() == null
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        String companyNumber = request.getCompanyNumber().trim();
        request.setCompanyNumber(companyNumber);
        request.setContactPersonFirstName(request.getContactPersonFirstName().trim());
        request.setContactPersonLastName(request.getContactPersonLastName().trim());
        request.setContactPersonEmail(request.getContactPersonEmail().trim());
        if (StringUtils.isEmpty(request.getCompanyNumber()) ||
                StringUtils.isEmpty(request.getContactPersonFirstName()) ||
                StringUtils.isEmpty(request.getContactPersonLastName()) ||
                !EmailValidator.getInstance().isValid(request.getContactPersonEmail())
        ) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        if (!companyNumber.startsWith("RC")) {
            if (!companyNumber.startsWith("BN")) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_COMPANY_NUMBER);
            }
        }
        KeycloakService keycloakService = IdentityManagerContext.getBean(KeycloakService.class);
        if (keycloakService.doesEmailExist(request.getContactPersonEmail())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.EMAIL_ALREADY_EXISTS);
        }
    }

    public static void validateOrganizationInviteRequestForCac(InviteOrganizationRequest request) throws InvalidArgumentException {
        if (StringUtils.isEmpty(request.getCompanyNumber())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_COMPANY_NUMBER);
        }
        String companyNumber = request.getCompanyNumber().trim();
        request.setCompanyNumber(companyNumber);
        if (StringUtils.isEmpty(request.getCompanyNumber())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_COMPANY_NUMBER);
        }
        if (!companyNumber.startsWith("RC")) {
            if (!companyNumber.startsWith("BN")) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_COMPANY_NUMBER);
            }
        }
        OrganizationOutputPort outputPort = IdentityManagerContext.getBean(OrganizationOutputPort.class);
        if (outputPort.existsByCompanyNumber(request.getCompanyNumber())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.ORGANIZATION_ALREADY_EXISTS);
        }

    }
}
