package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence;

import com.example.OnboardingIdentityManagementService.application.ports.output.users.UserOutputPort;
import com.example.OnboardingIdentityManagementService.domain.model.users.UserDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformUserPersistenceAdapter implements UserOutputPort {

    private final UserRepository userRepository;


    @Override
    public UserDomainObject save(UserDomainObject userDomainObject) {
        KarraboPlatformUser platformUser = userDomainObject.toKarraboPlatformUserEntity();
        platformUser = userRepository.save(platformUser);
        return new UserDomainObject(platformUser);
    }

}
