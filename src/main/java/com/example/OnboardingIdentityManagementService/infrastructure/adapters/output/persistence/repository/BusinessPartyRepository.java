package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository;

import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessPartyRepository extends JpaRepository<KarraboBusinessParty, Long> {
}
