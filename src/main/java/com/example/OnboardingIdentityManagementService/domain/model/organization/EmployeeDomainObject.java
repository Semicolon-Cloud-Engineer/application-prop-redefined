package com.example.OnboardingIdentityManagementService.domain.model.organization;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.OrganizationEmployee;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDomainObject {

    private String id;

    private KarraboOrganization organization;

    private KarraboPlatformUser employee;

    public EmployeeDomainObject(OrganizationEmployee employee) {
        this.id = employee.getId();
        this.organization = employee.getOrganization();
        this.employee = employee.getEmployee();
    }

    public OrganizationEmployee toEmployeeEntity() {
        return OrganizationEmployee.builder()
                .employee(employee)
                .organization(organization)
                .build();
    }

    public EmployeeDomainObject(CreateEmployeeAccountResponse response, InviteEmployeeRequest request) {
        this.organization = request.getOrganization();
        this.employee = response.getEmployeeUser();
    }

}
