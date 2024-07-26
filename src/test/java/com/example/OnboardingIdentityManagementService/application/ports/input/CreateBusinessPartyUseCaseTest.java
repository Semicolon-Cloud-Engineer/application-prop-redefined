package com.example.OnboardingIdentityManagementService.application.ports.input;

import com.example.OnboardingIdentityManagementService.domain.exception.BusinessPartyException;
import com.example.OnboardingIdentityManagementService.domain.model.businessParty.BusinessPartyDomainObject;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ExceptionMessageConstants;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.enums.BusinessPartyType;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.repository.BusinessPartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@SpringBootTest
class CreateBusinessPartyUseCaseTest {

    @Autowired
    private CreateBusinessPartyUseCase createBusinessPartyUseCase;

    @MockBean
    private BusinessPartyRepository businessPartyRepository;

    private String companyFullName;

    @BeforeEach
    void createDifferentNames() {
        companyFullName = "textCompany" + System.currentTimeMillis();
        when(businessPartyRepository.save(any())).thenReturn(new KarraboBusinessParty("id123", companyFullName, BusinessPartyType.ORGANISATION));
    }

    @Test
    void createBusinessParty() {
        CreateBusinessPartyRequest request = new CreateBusinessPartyRequest(companyFullName, BusinessPartyType.ORGANISATION);
        BusinessPartyDomainObject domainObject;
        try {
            domainObject = createBusinessPartyUseCase.createBusinessParty(request);
        } catch (BusinessPartyException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(domainObject);
        assertEquals(domainObject.getFullName(), request.getFullName());
        assertEquals(domainObject.getBusinessPartyType(), request.getBusinessPartyType());
    }

    @Test
    void createBusinessPartyWithNullName() {
        CreateBusinessPartyRequest request = new CreateBusinessPartyRequest(null, BusinessPartyType.ORGANISATION);
        assertThrows(BusinessPartyException.class, () -> createBusinessPartyUseCase.createBusinessParty(request), ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
    }

    @Test
    void createBusinessPartyWithEmptyName() {
        CreateBusinessPartyRequest request = new CreateBusinessPartyRequest("", BusinessPartyType.ORGANISATION);
        assertThrows(BusinessPartyException.class, () -> createBusinessPartyUseCase.createBusinessParty(request), ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
    }

    @Test
    void createBusinessPartyWithWhiteSpaceName() {
        CreateBusinessPartyRequest request = new CreateBusinessPartyRequest("       ", BusinessPartyType.ORGANISATION);
        assertThrows(BusinessPartyException.class, () -> createBusinessPartyUseCase.createBusinessParty(request), ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
    }

    @Test
    void createBusinessPartyWithNullPartyType() {
        CreateBusinessPartyRequest request = new CreateBusinessPartyRequest(companyFullName, null);
        assertThrows(BusinessPartyException.class, () -> createBusinessPartyUseCase.createBusinessParty(request), ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
    }
}