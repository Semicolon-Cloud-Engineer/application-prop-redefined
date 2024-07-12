package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessPartyRepository extends MongoRepository<KarraboBusinessParty, String> {
}
