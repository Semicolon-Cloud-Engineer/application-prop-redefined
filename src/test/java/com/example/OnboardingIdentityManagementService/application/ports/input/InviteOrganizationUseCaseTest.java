package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.OrganizationException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.OrganizationType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.OrganizationEmployee;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.BusinessPartyRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.EmployeeRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.OrganizationRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class InviteOrganizationUseCaseTest {

    @Autowired
    private InviteOrganizationUseCase inviteOrganizationUseCase;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private BusinessPartyRepository businessPartyRepository;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private UserRepository userRepository;

    private String firstName;
    private String lastName;
    private String email;

    @Value("${qoreId.rc-number}")
    private String companyNumber;

    @BeforeEach
    void createDifferentNames() {
        keycloakService.deleteTestData();
        firstName = "testName " + System.currentTimeMillis();
        lastName = "testName " + System.currentTimeMillis();
        email = "test-mail" + System.currentTimeMillis() + "@gmail.com";

        ClientRepresentation clientRepresentation = new ClientRepresentation();
        String QORE_ID_TEST_COMPANY_NAME = "TEST COMPANY";
        clientRepresentation
                .setClientId(QORE_ID_TEST_COMPANY_NAME);
        clientRepresentation.setId(QORE_ID_TEST_COMPANY_NAME);
        clientRepresentation.setName(QORE_ID_TEST_COMPANY_NAME);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(email);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setId("id-12345");

        KarraboBusinessParty businessPartyUser = new
                KarraboBusinessParty("id123", firstName + " " + lastName,
                BusinessPartyType.USER);

        KarraboBusinessParty businessPartyOrganization = new
                KarraboBusinessParty("id123", QORE_ID_TEST_COMPANY_NAME,
                BusinessPartyType.ORGANISATION);


        KarraboPlatformUser platformUser = new KarraboPlatformUser();
        platformUser.setFirstName(firstName);
        platformUser.setLastName(lastName);
        platformUser.setEmail(email);
        platformUser.setUserId("userId");
        platformUser.setBusinessParty(businessPartyUser);
        KarraboOrganization organization = new KarraboOrganization();
        organization.setOrganizationId("organizationId");
        organization.setBusinessParty(businessPartyOrganization);
        organization.setCompanyNumber("100001");
        OrganizationEmployee employee = new OrganizationEmployee("id12345", organization, platformUser);

        when(businessPartyRepository.save(any())).thenReturn(businessPartyUser);
        when(userRepository.save(any())).thenReturn(platformUser);
        when(employeeRepository.save(any())).thenReturn(employee);
        when(organizationRepository.save(any())).thenReturn(organization);

        try {
            when(keycloakService.getClient(any())).thenReturn(clientRepresentation);
            when(keycloakService.createClient(any())).thenReturn(clientRepresentation);
            when(keycloakService.doesClientExist(any())).thenReturn(true);
            when(keycloakService.createUser(any())).thenReturn(userRepresentation);
        } catch (KeycloakException | UserException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void inviteOrganization() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        InviteOrganizationResponse response;
        try {
            response = inviteOrganizationUseCase.inviteOrganization(request);
        } catch (OrganizationException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(response);
        assertEquals(companyNumber, "RC" + response.getCompanyNumber());
        assertEquals(firstName, response.getEmployeeData().getFirstName());
        assertEquals(lastName, response.getEmployeeData().getLastName());
    }

    @Test
    void inviteOrganizationWithNullCompanyNumber() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(null,
                        firstName, lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithNullContactPersonFirstName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        null, lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithNullContactPersonLastName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, null, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithNullContactPersonEmail() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, lastName, null, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithNullOrganizationType() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, lastName, email, null);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithEmptyCompanyNumber() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest("",
                        firstName, lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithEmptyContactPersonFirstName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        "", lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithEmptyContactPersonLastName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, "", email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithEmptyContactPersonEmail() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, lastName, "", OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithWhiteSpaceCompanyNumber() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest("      ",
                        firstName, lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithWhiteSpaceContactPersonFirstName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        "      ", lastName, email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithWhiteSpaceContactPersonLastName() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, "        ", email, OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }

    @Test
    void inviteOrganizationWithWhiteSpaceContactPersonEmail() {
        InviteOrganizationRequest request =
                new InviteOrganizationRequest(companyNumber,
                        firstName, lastName, "     ", OrganizationType.MERCHANT_ACQUIRER);
        assertThrows(OrganizationException.class,
                () -> inviteOrganizationUseCase.inviteOrganization(request));
    }


}
