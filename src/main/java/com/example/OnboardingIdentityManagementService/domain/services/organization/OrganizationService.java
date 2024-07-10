package com.example.OnboardingIdentityManagementService.domain.services.organization;

import com.example.OnboardingIdentityManagementService.application.ports.input.CreateBusinessPartyUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.input.InviteEmployeeUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.input.InviteOrganizationUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.identityPartners.BusinessIdentityServiceUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.organization.OrganizationOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.*;
import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.domain.model.organization.OrganizationDomainObject;
import com.example.OnboardingIdentityManagementService.domain.validtor.organization.OrganizationValidator;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.CacRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteEmployeeResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.mapper.OrganizationMapper;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService implements InviteOrganizationUseCase {

    private final BusinessIdentityServiceUseCase businessIdentity;
    private final OrganizationOutputPort organizationOutputPort;
    private final KeycloakService keycloakService;
    private final InviteEmployeeUseCase inviteEmployeeUseCase;
    private final CreateBusinessPartyUseCase createBusinessPartyUseCase;

    @Override
    public InviteOrganizationResponse inviteOrganization(InviteOrganizationRequest merchantAcquirerRequest) throws OrganizationException {
        try {
            OrganizationValidator.validateOrganizationInviteRequest(merchantAcquirerRequest);
            CacRequest newCacRequest = getCacRequestFromInviteOrganizationRequestObject(merchantAcquirerRequest);
            BusinessIdentityResponse businessIdentityResponse = businessIdentity.verifyCompanyCacNumber(newCacRequest);
            ClientRepresentation clientRepresentation = keycloakService.createClient(businessIdentityResponse.getCompanyName());
            BusinessPartyDomainObject businessParty = createBusinessPartyUseCase.createBusinessParty(
                    CreateBusinessPartyRequest.builder()
                            .fullName(businessIdentityResponse.getCompanyName())
                            .businessPartyType(BusinessPartyType.ORGANISATION)
                            .build());
            ClientRepresentation fetchedRepresentation = keycloakService.getClient(businessIdentityResponse.getCompanyName());
            OrganizationDomainObject organizationDomainObject = new OrganizationDomainObject(businessIdentityResponse);
            organizationDomainObject.setBusinessParty(businessParty.toBusinessPartyEntity());
            organizationDomainObject.setOrganizationId(fetchedRepresentation.getId());
            organizationDomainObject = organizationOutputPort.save(organizationDomainObject);
            InviteEmployeeRequest inviteEmployeeRequest = getInviteEmployeeRequestObjectFromData(clientRepresentation.getClientId(), merchantAcquirerRequest);
            inviteEmployeeRequest.setOrganization(organizationDomainObject.toOrganizationEntity());
            InviteEmployeeResponse employeeResponse = inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest);
            InviteOrganizationResponse organizationResponse = OrganizationMapper.convertOrganizationDomainObjectToInviteOrganizationResponse(organizationDomainObject);
            organizationResponse.setEmployeeData(employeeResponse);
            return organizationResponse;
        } catch (InvalidArgumentException | IdentityException | KeycloakException | EmployeeException |
                 BusinessPartyException e) {
            throw new OrganizationException(e);
        }
    }

    public InviteEmployeeRequest getInviteEmployeeRequestObjectFromData(String clientName, InviteOrganizationRequest organizationRequest) {
        return InviteEmployeeRequest.builder()
                .firstName(organizationRequest.getContactPersonFirstName().trim())
                .lastName(organizationRequest.getContactPersonLastName().trim())
                .email(organizationRequest.getContactPersonEmail().trim())
                .nameOfClientCreatedForCompany(clientName)
                .build();
    }

    public CacRequest getCacRequestFromInviteOrganizationRequestObject(InviteOrganizationRequest merchantAcquirerRequest) throws InvalidArgumentException {
        OrganizationValidator.validateOrganizationInviteRequestForCac(merchantAcquirerRequest);
        return CacRequest.builder()
                .companyNumber(merchantAcquirerRequest.getCompanyNumber())
                .build();
    }

}
