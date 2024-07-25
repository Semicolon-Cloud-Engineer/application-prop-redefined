package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.OrganizationException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.OrganizationType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
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

    private String firstName;
    private String lastName;
    private String email;

    @Value("${qoreId.rc-number}")
    private String companyNumber;

    private final String QORE_ID_TEST_COMPANY_NAME = "TEST COMPANY";

    @BeforeEach
    void createDifferentNames() {
        keycloakService.deleteTestData();
        firstName = "testName " + System.currentTimeMillis();
        lastName = "testName " + System.currentTimeMillis();
        email = "test-mail" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    void inviteOrganization() throws KeycloakException, UserException {
        String QORE_ID_TEST_COMPANY_NAME = "TEST COMPANY";
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation
                .setClientId(QORE_ID_TEST_COMPANY_NAME);
        clientRepresentation.setId(QORE_ID_TEST_COMPANY_NAME);
        clientRepresentation.setName(QORE_ID_TEST_COMPANY_NAME);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(email);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setId("id-12345");

        when(keycloakService.getClient(any())).thenReturn(clientRepresentation);
        when(keycloakService.createClient(any())).thenReturn(clientRepresentation);
        when(keycloakService.doesClientExist(any())).thenReturn(true);
        when(keycloakService.createUser(any())).thenReturn(userRepresentation);
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
        try {
            keycloakService.deleteClient(QORE_ID_TEST_COMPANY_NAME);
        } catch (KeycloakException e) {
            log.error("Error ", e);
        }
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
