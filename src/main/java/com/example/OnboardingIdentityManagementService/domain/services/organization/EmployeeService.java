package com.example.OnboardingIdentityManagementService.domain.services.organization;

import com.example.OnboardingIdentityManagementService.application.ports.input.CreateEmployeeAccountUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.input.InviteEmployeeUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.organization.EmployeeOutputPort;
import com.example.OnboardingIdentityManagementService.domain.exception.EmployeeException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.KeycloakException;
import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.domain.model.organization.EmployeeDomainObject;
import com.example.OnboardingIdentityManagementService.domain.validtor.organization.EmployeeValidator;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteEmployeeResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService implements InviteEmployeeUseCase {

    private final CreateEmployeeAccountUseCase createEmployeeUseCase;
    private final EmployeeOutputPort employeeOutputPort;

    @Override
    public InviteEmployeeResponse inviteEmployee(InviteEmployeeRequest request) throws EmployeeException {
        try {
            EmployeeValidator.validateInviteEmployeeSystemOwnerAccountRequest(request);
            CreateEmployeeAccountResponse response = createEmployeeUseCase.createAccountForEmployee(request);
            EmployeeDomainObject employeeDomainObject = new EmployeeDomainObject(response, request);
            employeeDomainObject = employeeOutputPort.save(employeeDomainObject);
            return getInviteEmployeeResponseObjectFromEmployeeAccount(employeeDomainObject);
        } catch (InvalidArgumentException | UserException | KeycloakException e) {
            throw new EmployeeException(e);
        }
    }

    public static InviteEmployeeResponse getInviteEmployeeResponseObjectFromEmployeeAccount(EmployeeDomainObject response) {
        return InviteEmployeeResponse.builder()
                .id(response.getId())
                .firstName(response.getEmployee().getFirstName())
                .lastName(response.getEmployee().getLastName())
                .build();
    }

}
