package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<KarraboOrganization, String> {

    boolean existsByCompanyNumber(String companyNumber);

}
