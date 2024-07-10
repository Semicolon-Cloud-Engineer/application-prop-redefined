package com.example.OnboardingIdentityManagementService.domain.validtor.general;

import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.businessParty.CreateBusinessPartyRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.utils.constants.ExceptionMessageConstants;
import org.apache.commons.lang3.StringUtils;

public class BusinessPartyValidator {

    public static void validateCreateBusinessPartyRequest(CreateBusinessPartyRequest createBusinessPartyRequest) throws InvalidArgumentException {
        if (StringUtils.isEmpty(createBusinessPartyRequest.getFullName()) || createBusinessPartyRequest.getBusinessPartyType() == null) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
        createBusinessPartyRequest.setFullName(createBusinessPartyRequest.getFullName().trim());
        if (StringUtils.isEmpty(createBusinessPartyRequest.getFullName())) {
            throw new InvalidArgumentException(ExceptionMessageConstants.INVALID_DETAILS_PROVIDED);
        }
    }
}
