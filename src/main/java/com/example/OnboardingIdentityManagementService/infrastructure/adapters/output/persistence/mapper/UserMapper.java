package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.mapper;

import com.example.OnboardingIdentityManagementService.domain.model.users.UserDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.users.CreateEmployeeAccountResponse;

public class UserMapper {

    public static CreateEmployeeAccountResponse convertUserDomainObjectToCreateEmployeeAccountResponse(UserDomainObject user) {
        return CreateEmployeeAccountResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userId(user.getUserId())
                .employeeUser(user.toKarraboPlatformUserEntity())
                .build();
    }
}
