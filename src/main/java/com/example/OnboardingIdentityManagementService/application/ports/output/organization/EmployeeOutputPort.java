package com.example.OnboardingIdentityManagementService.application.ports.output.organization;

import com.example.OnboardingIdentityManagementService.domain.model.organization.EmployeeDomainObject;

public interface EmployeeOutputPort {

    EmployeeDomainObject save(EmployeeDomainObject employeeDomainObject);
}
