package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends MongoRepository<KarraboOrganization, String> {

    boolean existsByCompanyNumber(String companyNumber);

}
