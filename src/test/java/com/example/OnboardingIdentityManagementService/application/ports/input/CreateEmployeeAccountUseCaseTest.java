package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.domain.services.users.UserService;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
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
class CreateEmployeeAccountUseCaseTest {

    @Autowired
    private CreateEmployeeAccountUseCase createEmployeeAccountUseCase;

    @Autowired
    private KeycloakService keycloakService;

    @MockBean
    private UserRepository userRepository;

    private String clientName;

    private String email;

    private String firstName;

    private String lastName;

    @BeforeEach
    void createDifferentNames() {
        keycloakService.deleteTestData();
        clientName = "testClient " + System.currentTimeMillis();
        firstName = "testUser " + System.currentTimeMillis();
        lastName = "testUserLastName " + System.currentTimeMillis();
        email = String.format("%s@gmail.com", "testUser" + System.currentTimeMillis()).toLowerCase();
    }

    @Test
    void createAccountForEmployee() {
        when(userRepository.save(any())).thenReturn(new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email));
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);

        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }

        CreateEmployeeAccountResponse response;
        try {
            response = createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(response);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(email, response.getEmail());
    }

    @Test
    void createAccountForEmployeeWithEmailThatAlreadyExists() {
        when(userRepository.save(any())).thenReturn(new KarraboPlatformUser("falseId",new KarraboBusinessParty(), firstName, lastName, email));
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);

        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }

        CreateEmployeeAccountResponse response;
        try {
            response = createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(response);
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(email, response.getEmail());

        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithEmptyFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName("");
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithNullFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(null);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithWhiteSpaceFirstName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName("    ");
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithEmptyLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName("");
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithNullLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(null);
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithWhiteSpaceLastName() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName("       ");
        inviteEmployeeRequest.setEmail(email);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithEmptyEmail() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail("");
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithNullEmail() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(null);
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void createAccountForEmployeeWithWhiteSpaceEmail() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail("    ");
        inviteEmployeeRequest.setNameOfClientCreatedForCompany(clientName);
        assertThrows(UserException.class, () -> createEmployeeAccountUseCase.createAccountForEmployee(inviteEmployeeRequest));
    }

    @Test
    void getUserDtoFromInviteEmployeeRequest() {
        InviteEmployeeRequest inviteEmployeeRequest = new InviteEmployeeRequest();
        inviteEmployeeRequest.setFirstName(firstName);
        inviteEmployeeRequest.setLastName(lastName);
        inviteEmployeeRequest.setEmail(email);

        KarraboRequest karraboRequest = UserService.getUserDtoFromInviteEmployeeRequest(inviteEmployeeRequest);

        assertEquals(inviteEmployeeRequest.getFirstName(), karraboRequest.getFirstName());
        assertEquals(inviteEmployeeRequest.getLastName(), karraboRequest.getLastName());
        assertEquals(inviteEmployeeRequest.getEmail(), karraboRequest.getEmail());
    }

}