package com.example.OnboardingIdentityManagementService.domain.services.keycloak;

import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import org.apache.pulsar.client.api.PulsarClientException;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {

    ClientRepresentation createClient(String clientName) throws KeycloakException;

    ClientRepresentation getClient(String clientName) throws KeycloakException;

    void deleteClient(String clientName) throws KeycloakException;

    void deleteTestData();

    RealmResource getRealmResource(String realmName);

    void produceMessage(String message) throws PulsarClientException;

    UserRepresentation createUser(KarraboRequest userRequestDto) throws UserException;

    boolean doesEmailExist(String email) throws KeycloakException;

    boolean doesClientExist(String nameOfClientCreatedForCompany) throws KeycloakException;
}
