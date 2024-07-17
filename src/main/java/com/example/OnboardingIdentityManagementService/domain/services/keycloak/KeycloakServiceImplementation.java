package com.example.OnboardingIdentityManagementService.domain.services.keycloak;


import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.validtor.users.UserValidator;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.pulsar.PulsarComponent;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.pulsar.client.api.PulsarClientException;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.admin.client.Keycloak;
import org.apache.commons.lang3.StringUtils;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImplementation implements KeycloakService {

    private final Keycloak keycloak;
    private final PulsarComponent pulsarComponent;

    @Value("${keycloak.realm}")
    private String KEYCLOAK_REALM;

    @Override
    public ClientRepresentation createClient(String clientName) throws KeycloakException {
        if (StringUtils.isEmpty(clientName)) {
            throw new KeycloakException("Client name cannot be empty");
        }
        clientName = clientName.trim();
        if (StringUtils.isEmpty(clientName)) {
            throw new KeycloakException("Client name cannot be empty");
        }
        if (doesClientExist(clientName)) {
            throw new KeycloakException(ExceptionMessageConstants.ORGANIZATION_CLIENT_ALREADY_EXISTS);
        }
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(clientName);
        clientRepresentation.setDirectAccessGrantsEnabled(Boolean.TRUE);
        clientRepresentation.setPublicClient(Boolean.TRUE);
        try (Response response = keycloak.realm(KEYCLOAK_REALM).clients().create(clientRepresentation)) {
            if (response.getStatusInfo().equals(Response.Status.CONFLICT)) {
                throw new KeycloakException(ExceptionMessageConstants.ORGANIZATION_CLIENT_ALREADY_EXISTS);
            }
        }
        return getClient(clientRepresentation.getClientId());
    }

    @Override
    public ClientRepresentation getClient(String clientName) throws KeycloakException {
        if (StringUtils.isEmpty(clientName)) {
            throw new KeycloakException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
        }
        clientName = clientName.trim();
        if (StringUtils.isEmpty(clientName)) {
            throw new KeycloakException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
        }
        RealmResource realmResource = getRealmResource(KEYCLOAK_REALM);
        ClientsResource clientsResource = realmResource.clients();
        List<ClientRepresentation> resources = clientsResource.findAll();
        for (ClientRepresentation aClient : resources) {
            if (aClient.getClientId().equals(clientName)) {
                return aClient;
            }
        }
        throw new KeycloakException(ExceptionMessageConstants.ORGANIZATION_CLIENT_NOT_FOUND);
    }

    @Override
    public void deleteClient(String clientName) throws KeycloakException {
        try {
            if (StringUtils.isEmpty(clientName)) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
            }
            if (StringUtils.isEmpty(clientName.trim())) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
            }
            ClientResource clientResource = getClientResource(clientName);
            clientResource.remove();
        } catch (InvalidArgumentException e) {
            throw new KeycloakException(e);
        }
    }

    @Override
    public void deleteTestData() {
        List<ClientRepresentation> clientRepresentationList = keycloak.realm(KEYCLOAK_REALM).clients().findAll();
        if (clientRepresentationList != null && !clientRepresentationList.isEmpty()) {
            clientRepresentationList.forEach(clientRepresentation -> {
                if (clientRepresentation.getClientId().startsWith("test")) {
                    try {
                        deleteClient(clientRepresentation.getClientId());
                    } catch (KeycloakException e) {
                        log.error("Error: {}", e.getMessage());
                    }
                }
            });
        } else {
            log.info("Unable to get resource for client");
        }
    }

    @Override
    public RealmResource getRealmResource(String realmName) {
        if (StringUtils.isEmpty(realmName)) {
            throw new IllegalArgumentException("Realm name cannot be empty");
        }
        if (StringUtils.isEmpty(realmName.trim())) {
            throw new IllegalArgumentException("Realm name cannot be empty");
        }
        return keycloak.realm(realmName);
    }

    @Override
    public void produceMessage(String message) throws PulsarClientException {
        pulsarComponent.sendStringMessageToPulsarTopic(message);
    }

    @Override
    public UserRepresentation createUser(KarraboRequest userRequestDto) throws UserException {
        try {
            UserValidator.validateUserRequestDto(userRequestDto);
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEmail(userRequestDto.getEmail());
            userRepresentation.setUsername(userRequestDto.getEmail());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);
            userRepresentation.setFirstName(userRequestDto.getFirstName());
            userRepresentation.setLastName(userRequestDto.getLastName());
            try (Response response = keycloak.realm(KEYCLOAK_REALM).users().create(userRepresentation)) {
                if (response.getStatusInfo().equals(Response.Status.CONFLICT)) {
                    throw new UserException("User with the same email or username already exists.");
                }
            }
            List<UserRepresentation> foundUsers = keycloak.realm(KEYCLOAK_REALM).users().search(userRequestDto.getEmail());
            if (foundUsers.isEmpty()) {
                throw new UserException(ExceptionMessageConstants.USER_CREATION_FAILED);
            }
            return foundUsers.get(0);

        } catch (Exception e) {
            throw new UserException(e);
        }
    }

    @Override
    public boolean doesEmailExist(String email) throws KeycloakException {
        try {
            if (StringUtils.isEmpty(email) || !EmailValidator.getInstance().isValid(email)) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_EMAIL_ADDRESS);
            }
            if (StringUtils.isEmpty(email.trim())) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_EMAIL_ADDRESS);
            }
            UsersResource usersResource = keycloak.realm(KEYCLOAK_REALM).users();
            List<UserRepresentation> existingUsers = usersResource.list();
            for (UserRepresentation user : existingUsers) {
                if (user.getEmail().equals(email.toLowerCase())) {
                    return true;
                }
            }
            return false;
        } catch (InvalidArgumentException e) {
            throw new KeycloakException(e);
        }
    }

    @Override
    public boolean doesClientExist(String nameOfClientCreatedForCompany) throws KeycloakException {
        try {
            if (StringUtils.isEmpty(nameOfClientCreatedForCompany)) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
            }
            if (StringUtils.isEmpty(nameOfClientCreatedForCompany.trim())) {
                throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
            }
            RealmResource realmResource = getRealmResource(KEYCLOAK_REALM);
            ClientsResource clientsResource = realmResource.clients();
            List<ClientRepresentation> resources = clientsResource.findAll();
            for (ClientRepresentation aClient : resources) {
                if (aClient.getClientId().equals(nameOfClientCreatedForCompany)) {
                    return true;
                }
            }
            return false;
        } catch (InvalidArgumentException e) {
            throw new KeycloakException(e);
        }
    }


    private ClientResource getClientResource(String clientName) throws KeycloakException, InvalidArgumentException {
        if (StringUtils.isEmpty(clientName)) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
        }
        if (StringUtils.isEmpty(clientName.trim())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_CLIENT_NAME);
        }
        String clientId = getClient(clientName).getId();
        return keycloak.realm(KEYCLOAK_REALM).clients().get(clientId);
    }


}
