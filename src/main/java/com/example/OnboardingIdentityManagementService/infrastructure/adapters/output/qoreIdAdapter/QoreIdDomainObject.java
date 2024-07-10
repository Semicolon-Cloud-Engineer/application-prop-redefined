package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.qoreIdAdapter;

import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.model.enums.Gender;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.*;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class QoreIdDomainObject {


    private static ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(QoreIdDomainObject.class);


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public QoreIdDomainObject(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    public static BvnResponse convertQoreIdBvnResponseToBvnResponse(QoreIdBvnResponse qoreIdBvnResponse) throws InvalidArgumentException {
        BvnResponse bvnResponse = modelMapper.map(qoreIdBvnResponse.getBvn(), BvnResponse.class);
        bvnResponse.setGender(convertGenderStringToGenderEnum(qoreIdBvnResponse.getBvn().getGender()));
        bvnResponse.setDateOfBirth(LocalDate.parse(qoreIdBvnResponse.getBvn().getBirthdate(), DATE_FORMATTER));
        return bvnResponse;
    }

    public static NinResponse convertQoreIdINinResponseToNinResponse(QoreIdNinResponse qoreIdNinResponse) throws InvalidArgumentException {
        NinResponse ninResponse = modelMapper.map(qoreIdNinResponse.getNin(), NinResponse.class);
        ninResponse.setGender(convertGenderStringToGenderEnum(qoreIdNinResponse.getNin().getGender()));
        ninResponse.setBirthDate(LocalDate.parse(qoreIdNinResponse.getNin().getBirthdate(), DATE_FORMATTER));
        ninResponse.setAddress(concatenateResidenceToAddress(qoreIdNinResponse));
        return ninResponse;
    }

    private static Gender convertGenderStringToGenderEnum(String gender) throws InvalidArgumentException {
        if (StringUtils.isEmpty(gender)) {
            throw new InvalidArgumentException("Error fetching user information");
        }
        switch (gender.trim().toUpperCase()) {
            case "M":
                return Gender.MALE;
            case "F":
                return Gender.FEMALE;
            default:
                return Gender.OTHERS;
        }
    }

    private static String concatenateResidenceToAddress(QoreIdNinResponse qoreIdNinResponse) {
        String address = "";
        address += qoreIdNinResponse.getNin().getResidence().getAddress1().trim() + " ";
        address += qoreIdNinResponse.getNin().getResidence().getLga().trim() + " ";
        address += qoreIdNinResponse.getNin().getResidence().getState().trim();
        return address.trim();
    }

    public static BusinessIdentityResponse convertBusinessVerificationResponseToBusinessResponse(QoreIdBusinessIdentityVerificationResponse qoreIdBusinessResponse) {
        return modelMapper.map(qoreIdBusinessResponse.getCac(), BusinessIdentityResponse.class);
    }
}
