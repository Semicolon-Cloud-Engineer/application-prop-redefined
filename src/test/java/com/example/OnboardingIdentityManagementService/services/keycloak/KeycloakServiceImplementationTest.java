package com.example.OnboardingIdentityManagementService.services.keycloak;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class KeycloakServiceImplementationTest {

    @Autowired
    private KeycloakService keycloakService;

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
    void createClientWithNullClientName() {
        assertThrows(KeycloakException.class, () -> keycloakService.createClient(null));
    }

    @Test
    void createClientWithEmptyClientName() {
        assertThrows(KeycloakException.class, () -> keycloakService.createClient(""));
    }

    @Test
    void getClientWithNullClientName() {
        assertThrows(KeycloakException.class, () -> keycloakService.getClient(null));
    }

    @Test
    void getClientWithEmptyClientName() {
        assertThrows(KeycloakException.class, () -> keycloakService.getClient(""));
    }

    @Test
    void getClientThatDoesNotExist() {
        assertThrows(KeycloakException.class,
                () -> keycloakService.getClient("invalidClient"),
                ExceptionMessageConstants.ORGANIZATION_CLIENT_NOT_FOUND);
    }

    @Test
    void createClientInRealm() {
        // covers test case for createClient and getClient methods
        ClientRepresentation representation = null;
        try {
            representation = keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            log.error("Error while creating client: {}", e.getMessage());
        }
        assertNotNull(representation);
        assertEquals(clientName, representation.getClientId());
        ClientRepresentation foundClient;
        try {
            foundClient = keycloakService.getClient(representation.getClientId());
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(foundClient);
        assertEquals(representation.getClientId(), foundClient.getClientId());
    }

    @Test
    void cannotCreateClientWithSameNameInRealm() {
        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            log.error("Error: {}", e.getMessage());
        }
        assertThrows(KeycloakException.class,
                () -> keycloakService.createClient(clientName),
                "Client with that name exists already");
    }

    @Test
    void deleteClientInRealm() {
        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            log.error("Error here: {}", e.getMessage());
        }
        ClientRepresentation foundClient;
        try {
            foundClient = keycloakService.getClient(clientName);
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(foundClient);
        assertEquals(clientName, foundClient.getClientId());
        assertDoesNotThrow(() -> keycloakService.deleteClient(clientName));
        assertThrows(KeycloakException.class,
                () -> keycloakService.getClient(clientName),
                "Client not found");
    }

    @Test
    void cannotDeleteClientWithNullClientName() {
        assertThrows(KeycloakException.class,
                () -> keycloakService.deleteClient(null),
                "Client name cannot be empty");
    }

    @Test
    void cannotDeleteClientWithEmptyClientName() {
        assertThrows(KeycloakException.class,
                () -> keycloakService.deleteClient(""),
                "Client name cannot be empty");
    }


//    @Test
//    void getRealmResource() {
//        RealmResource resource = keycloakService.getRealmResource(KEYCLOAK_REALM);
//        assertEquals(resource.toRepresentation().getDisplayName(), KEYCLOAK_REALM);
//    }

    @Test
    void getRealmWithNullRealmName() {
        assertThrows(IllegalArgumentException.class, () -> keycloakService.getRealmResource(null));
    }

    @Test
    void getRealmWithEmptyRealmName() {
        assertThrows(IllegalArgumentException.class, () -> keycloakService.getRealmResource(""));
    }

    @Test
    void createUserSuccessfully() {
        KarraboRequest userRequest = new KarraboRequest(firstName, lastName, email, "password123", "", LocalDate.of(2002, 12, 12));
        UserRepresentation createdUser;
        try {
            createdUser = keycloakService.createUser(userRequest);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(createdUser);
        assertEquals(email, createdUser.getEmail());
        assertEquals(firstName, createdUser.getFirstName());
        assertEquals(lastName, createdUser.getLastName());
    }

    @Test
    void doesEmailExist() {
        KarraboRequest userRequest = new KarraboRequest(firstName, lastName, email, "password123", "", LocalDate.of(2002, 12, 12));
        UserRepresentation createdUser;
        try {
            createdUser = keycloakService.createUser(userRequest);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(createdUser);
        try {
            assertTrue(keycloakService.doesEmailExist(email));
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
        try {
            assertFalse(keycloakService.doesEmailExist("email@email.com"));
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void doesClientExist() {
        try {
            keycloakService.createClient(clientName);
        } catch (KeycloakException e) {
            log.error("Error creating client: {}", e.getMessage());
        }
        try {
            assertTrue(keycloakService.doesClientExist(clientName));
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
        try {
            assertFalse(keycloakService.doesClientExist("randomClient"));
        } catch (KeycloakException e) {
            throw new RuntimeException(e);
        }
    }
}