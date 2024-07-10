package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.EmployeeException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.model.organization.EmployeeDomainObject;
import com.example.OnboardingIdentityManagementService.domain.services.organization.EmployeeService;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteEmployeeResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.OrganizationEmployee;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.EmployeeRepository;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class InviteEmployeeUseCaseTest {

    @Autowired
    private InviteEmployeeUseCase inviteEmployeeUseCase;

    @Autowired
    private KeycloakService keycloakService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

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
    }



    @Test
    void getInviteEmployeeResponseObjectFromEmployeeAccountMethodTest() {
        EmployeeDomainObject domainObject = new EmployeeDomainObject();
        domainObject.setId(12334L);
        KarraboPlatformUser platformUser = new KarraboPlatformUser();
        platformUser.setFirstName(firstName);
        platformUser.setLastName(lastName);
        domainObject.setEmployee(platformUser);
        InviteEmployeeResponse response = EmployeeService.getInviteEmployeeResponseObjectFromEmployeeAccount(domainObject);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(12334L, response.getId());
    }

    @Test
    void inviteEmployee() {
        when(userRepository.save(any())).thenReturn(new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email));
        when(employeeRepository.save(any())).thenReturn(new OrganizationEmployee(12345L, new KarraboOrganization(),
                new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email)));
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        KarraboOrganization organization = new KarraboOrganization();
        inviteEmployeeRequest.setOrganization(organization);
        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            log.error("Error: ", e);
        }

        InviteEmployeeResponse response = null;
        try {
            response = inviteEmployeeUseCase.inviteEmployee(inviteEmployeeRequest);
        } catch (EmployeeException e) {
            log.error("Error: ", e);
        }
        assertNotNull(response);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(12345L, response.getId());
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