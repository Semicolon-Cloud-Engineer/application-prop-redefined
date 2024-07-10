package com.example.OnboardingIdentityManagementService.domain.services.users;


import com.example.OnboardingIdentityManagementService.application.ports.input.CreateBusinessPartyUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.input.CreateEmployeeAccountUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.users.UserOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.config.pulsar.PulsarComponent;
import com.example.OnboardingIdentityManagementService.domain.exception.BusinessPartyException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.domain.model.users.UserDomainObject;
import com.example.OnboardingIdentityManagementService.domain.validtor.users.UserValidator;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.KarraboRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.example.OnboardingIdentityManagementService.domain.services.keycloak.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements CreateEmployeeAccountUseCase {

    private final KeycloakService keycloakService;
    private final UserOutputPort userOutputPort;
    private final PulsarComponent pulsarComponent;
    private final CreateBusinessPartyUseCase createBusinessPartyUseCase;


    @Override
    public CreateEmployeeAccountResponse createAccountForEmployee(InviteEmployeeRequest request) throws UserException {
        try {
            UserValidator.validateInviteEmployeeRequest(request);
            UserRepresentation keycloakUser = keycloakService.createUser(getUserDtoFromInviteEmployeeRequest(request));
            BusinessPartyDomainObject businessParty = createBusinessPartyUseCase.createBusinessParty(
                    CreateBusinessPartyRequest.builder()
                            .fullName(request.getFirstName() + " " + request.getLastName())
                            .businessPartyType(BusinessPartyType.USER)
                    .build());
            UserDomainObject userDomainObject = new UserDomainObject(request);
            userDomainObject.setBusinessParty(businessParty.toBusinessPartyEntity());
            userDomainObject.setUserId(keycloakUser.getId());
            userDomainObject = userOutputPort.save(userDomainObject);
            pulsarComponent.sendNotificationEmail();
            return UserMapper.convertUserDomainObjectToCreateEmployeeAccountResponse(userDomainObject);
        } catch (InvalidArgumentException | BusinessPartyException | KeycloakException e) {
            throw new UserException(e);
        }
    }

    public static KarraboRequest getUserDtoFromInviteEmployeeRequest(InviteEmployeeRequest request) {
        KarraboRequest karraboRequest = new KarraboRequest();
        karraboRequest.setFirstName(request.getFirstName());
        karraboRequest.setLastName(request.getLastName());
        karraboRequest.setEmail(request.getEmail());
        return karraboRequest;
    }

}
