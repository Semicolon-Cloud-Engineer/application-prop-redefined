package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.OrganizationException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.OrganizationType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class InviteOrganizationUseCaseTest {

    @Autowired
    private InviteOrganizationUseCase inviteOrganizationUseCase;

    @Autowired
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
    void inviteOrganization() {
        try {
            if (keycloakService.doesClientExist(QORE_ID_TEST_COMPANY_NAME)) {
                keycloakService.deleteClient(QORE_ID_TEST_COMPANY_NAME);
            }
        } catch (KeycloakException e) {
            log.error("Error ", e);
        }
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
