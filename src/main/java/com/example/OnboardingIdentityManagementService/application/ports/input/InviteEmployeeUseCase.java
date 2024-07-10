package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.EmployeeException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteEmployeeResponse;

public interface InviteEmployeeUseCase {

    InviteEmployeeResponse inviteEmployee(InviteEmployeeRequest request) throws EmployeeException;

}
