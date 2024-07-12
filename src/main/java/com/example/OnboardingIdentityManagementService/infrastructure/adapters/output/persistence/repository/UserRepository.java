package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<KarraboPlatformUser, String> {
}
