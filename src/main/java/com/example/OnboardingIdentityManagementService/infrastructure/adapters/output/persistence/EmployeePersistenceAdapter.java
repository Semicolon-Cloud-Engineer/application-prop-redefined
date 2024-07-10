package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence;


import com.example.OnboardingIdentityManagementService.application.ports.output.organization.EmployeeOutputPort;
import com.example.OnboardingIdentityManagementService.domain.model.organization.EmployeeDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.OrganizationEmployee;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeePersistenceAdapter implements EmployeeOutputPort {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDomainObject save(EmployeeDomainObject employeeDomainObject) {
        OrganizationEmployee employee = employeeDomainObject.toEmployeeEntity();
        employee = employeeRepository.save(employee);
        return new EmployeeDomainObject(employee);
    }

}
