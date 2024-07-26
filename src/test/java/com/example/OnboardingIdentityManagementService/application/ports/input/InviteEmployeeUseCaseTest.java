package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.EmployeeException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.domain.model.organization.EmployeeDomainObject;
import com.example.OnboardingIdentityManagementService.domain.services.organization.EmployeeService;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteEmployeeResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.OrganizationEmployee;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.BusinessPartyRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.EmployeeRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class InviteEmployeeUseCaseTest {

    @Autowired
    private InviteEmployeeUseCase inviteEmployeeUseCase;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private BusinessPartyRepository businessPartyRepository;

    private String firstName;
    private String lastName;
    private String email;
    private String clientName;

    @BeforeEach
    void createDifferentNames() {
        firstName = "testName " + System.currentTimeMillis();
        lastName = "testName " + System.currentTimeMillis();
        email = "test-mail" + System.currentTimeMillis() + "@gmail.com";
        clientName = "test-client" + System.currentTimeMillis();

        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation
                .setClientId(clientName);
        clientRepresentation.setId(clientName);
        clientRepresentation.setName(clientName);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(email);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setId("id-12345");
        when(businessPartyRepository.save(any()))
                .thenReturn(new KarraboBusinessParty(
                        "id123", firstName + " " + lastName,
                        BusinessPartyType.USER));

        try {
            when(keycloakService.getClient(any())).thenReturn(clientRepresentation);
            when(keycloakService.createClient(any())).thenReturn(clientRepresentation);
            when(keycloakService.createUser(any())).thenReturn(userRepresentation);
        } catch (KeycloakException | UserException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    void getInviteEmployeeResponseObjectFromEmployeeAccountMethodTest() {
        EmployeeDomainObject domainObject = new EmployeeDomainObject();
        domainObject.setId("12334");
        KarraboPlatformUser platformUser = new KarraboPlatformUser();
        platformUser.setFirstName(firstName);
        platformUser.setLastName(lastName);
        domainObject.setEmployee(platformUser);
        InviteEmployeeResponse response = EmployeeService.getInviteEmployeeResponseObjectFromEmployeeAccount(domainObject);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals("12334", response.getId());
    }

    @Test
    void inviteEmployee() {
        when(userRepository.save(any())).thenReturn(new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email));
        when(employeeRepository.save(any())).thenReturn(new OrganizationEmployee("12345", new KarraboOrganization(),
                new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email)));
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);

        InviteEmployeeResponse response = null;
        try {
            when(keycloakService.doesClientExist(clientName)).thenReturn(true);
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
        try {
            response = inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest);
        } catch (EmployeeException e) {
            log.error("Error: ", e);
        }
        assertNotNull(response);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals("12345", response.getId());
    }

    @Test
    void inviteEmployeeWithNullFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(null);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithNullLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(null);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithNullEmail() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(null);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithEmptyFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName("");
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithEmptyLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName("");
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithEmptyEmail() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail("");
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithWhiteSpaceFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName("   ");
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithWhiteSpaceLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName("    ");
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

    @Test
    void inviteEmployeeWithoutOrganization() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(EmployeeException.class, () -> inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest));
    }

}