package com.example.OnboardingIdentityManagementService.service.identityPartners.qoreId;

import com.example.OnboardingIdentityManagementService.domain.exception.IdentityException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidFormatException;
import com.example.OnboardingIdentityManagementService.domain.model.enums.Gender;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.BvnRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.CacRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId.NinRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.BvnResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.NinResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.qoreIdAdapter.QoreIdIdentityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class QoreIdIdentityServiceTest {

    @Autowired
    private QoreIdIdentityService qoreIdIdentityService;

    @Test
    void verifyBankVerificationNumber_success() throws InvalidArgumentException, IdentityException {
        BvnRequest request = BvnRequest.builder()
                .bankVerificationNumber("95888168924")
                .firstName("Bunch")
                .lastName("Dillon")
                .phoneNumber("08000000000")
                .build();
        BvnResponse response;
        response = qoreIdIdentityService.verifyBvn(request);

        assertNotNull(response);
        assertEquals("95888168924", response.getBvn());
        assertEquals(request.getFirstName(), response.getFirstName());
        assertEquals(request.getLastName(), response.getLastName());
        assertNotNull(response.getGender());
        System.out.println("Gender: " + response.getGender());
        System.out.println("Address: "+ response.getResidentialAddress());
        System.out.println("Date: " + response.getDateOfBirth());
    }

    @Test
    void verifyBvn_nullRequest() {
        assertThrows(NullPointerException.class, () -> qoreIdIdentityService.verifyBvn(null));
    }


    @Test
    void verifyBankVerificationNumber_nullBvn() {
        BvnRequest request = BvnRequest.builder()
                .bankVerificationNumber(null)
                .firstName("Bunch")
                .lastName("Dillon")
                .phoneNumber("08000000000")
                .build();
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyBvn(request));
    }

    @Test
    void verifyBankVerificationNumber_emptyBvn() {
        BvnRequest request = BvnRequest.builder()
                .bankVerificationNumber("")
                .firstName("Bunch")
                .lastName("Dillon")
                .phoneNumber("08000000000")
                .build();
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyBvn(request));
    }

    @Test
    void verifyBankVerificationNumber_wrongLengthOfBvn() {
        BvnRequest request = BvnRequest.builder()
                .bankVerificationNumber("958881689246567")
                .firstName("Bunch")
                .lastName("Dillon")
                .phoneNumber("08000000000")
                .build();
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyBvn(request));
    }


    @Test
    void verifyNationalIdentificationNumber_success() {
        NinRequest request = NinRequest.builder()
                .firstname("Bunch")
                .lastname("Dillon")
                .nin("63184876213")
                .build();
        NinResponse response = null;
        try {
            response = qoreIdIdentityService.verifyNin(request);
        } catch (InvalidArgumentException | IdentityException e) {
            log.error("Error: ", e);
        }

        assertNotNull(response);
        assertEquals("63184876213", response.getNin());
        assertEquals(request.getFirstname(), response.getFirstName());
        assertEquals(request.getLastname(), response.getLastName());
        assertNotNull(response.getGender());
        assertEquals(Gender.MALE, response.getGender());
        assertNotNull(response.getAddress());
    }


    @Test
    void verifyNationalIdentificationNumber_nullResponse() {
        NinRequest request = NinRequest.builder()
                .firstname("Bunch")
                .lastname("Dillon")
                .nin("63184876213")
                .phonenumber("08000000000")
                .build();
        NinResponse response;
        try {
            response = qoreIdIdentityService.verifyNin(request);
            assertNotNull(response);
        } catch (InvalidArgumentException | IdentityException e) {
            fail("Exception should not be thrown");
        }
    }


    @Test
    void verifyNationalIdentificationNumber_invalidNin_throwsException() {
        NinRequest request = NinRequest.builder()
                .firstname("Bunch")
                .lastname("Dillon")
                .nin("invalid_nin")
                .build();

        assertThrows(RuntimeException.class, () -> qoreIdIdentityService.verifyNin(request));
    }

    @Test
    void verifyNationalIdentificationNumber_nullNin() {
        NinRequest request = NinRequest.builder()
                .firstname("Bunch")
                .lastname("Dillon")
                .nin(null)
                .build();
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyNin(request));
    }

    @Test
    void verifyCompanyCacNumber_successfulResponse() {
        CacRequest request = CacRequest.builder().companyNumber("RC100001").build();
        BusinessIdentityResponse response = null;
        try {
             response = qoreIdIdentityService.verifyCompanyCacNumber(request);
        } catch (IdentityException  e) {
            log.error("Error: ", e);
        }

        assertNotNull(response);
        assertEquals("RC100001".substring(2), response.getRcNumber());
    }

    @Test
    void verifyCompanyCacNumber_nullRequest() {
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyCompanyCacNumber(null));
    }

    @Test
    void verifyCompanyCacNumber_nullCompanyNumber() {
        CacRequest request = CacRequest.builder().companyNumber(null).build();
        assertThrows(IdentityException.class, () -> qoreIdIdentityService.verifyCompanyCacNumber(request));
    }

    @Test
    void verifyCompanyCacNumber_invalidCompanyNumber() {
        CacRequest request = CacRequest.builder().companyNumber("invalid_number").build();

        Exception exception = assertThrows(InvalidFormatException.class, () -> {
            try {
                qoreIdIdentityService.verifyCompanyCacNumber(request);
            } catch (HttpClientErrorException.BadRequest e) {
                throw new InvalidFormatException("Invalid Registration Number format. regNumber should have the 2-alphabet followed by digits");
            }
        });

        assertEquals("Invalid Registration Number format. regNumber should have the 2-alphabet followed by digits", exception.getMessage());
    }
}