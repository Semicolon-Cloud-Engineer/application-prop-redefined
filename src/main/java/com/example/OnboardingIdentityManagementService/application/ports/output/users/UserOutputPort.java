package com.example.OnboardingIdentityManagementService.application.ports.output.users;

import com.example.OnboardingIdentityManagementService.domain.model.users.UserDomainObject;

public interface UserOutputPort {

    UserDomainObject save(UserDomainObject userDomainObject);

}
