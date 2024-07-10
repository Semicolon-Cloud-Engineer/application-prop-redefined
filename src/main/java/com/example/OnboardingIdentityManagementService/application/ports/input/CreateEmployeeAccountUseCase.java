package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.UserException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;

public interface CreateEmployeeAccountUseCase {

    CreateEmployeeAccountResponse createAccountForEmployee(InviteEmployeeRequest request) throws UserException;

}
