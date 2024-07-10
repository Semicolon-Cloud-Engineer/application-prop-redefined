package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.qoreIdAdapter;

import com.example.OnboardingIdentityManagementService.application.ports.output.identityPartners.BusinessIdentityServiceUseCase;
import com.example.OnboardingIdentityManagementService.application.ports.output.identityPartners.DigitalIdentityServiceUseCase;
import com.example.OnboardingIdentityManagementService.domain.exception.IdentityException;
import com.example.OnboardingIdentityManagementService.domain.exception.InvalidArgumentException;
import com.example.OnboardingIdentityManagementService.domain.exception.QoreIdException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.BvnRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.karrabo.CacRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.identityPartners.qoreId.*;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.businessIdentity.BusinessIdentityResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.identityPartners.qoreId.*;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.utils.constants.ExceptionMessageConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class QoreIdIdentityService implements DigitalIdentityServiceUseCase, BusinessIdentityServiceUseCase {

    private static String token;
    private final RestClient restClient;

    @Value("${qoreId.client-id}")
    private String clientId;

    @Value("${qoreId.secret}")
    private String clientSecret;

    @Value("${qoreId.url}")
    private String BASE_URL;

    @PostConstruct
    @Scheduled(cron = "0 */45 */1 * * ?")
    private void renewAuthenticationToken() throws QoreIdException {
        QoreIdAuthenticationResponse response;
        try {
            response = restClient
                    .post()
                    .uri(BASE_URL + "/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.TEXT_PLAIN)
                    .body(QoreIdAuthenticationRequest
                            .builder()
                            .clientId(clientId)
                            .secret(clientSecret)
                            .build())
                    .retrieve()
                    .body(QoreIdAuthenticationResponse.class);
        } catch (Exception e) {
            throw new QoreIdException(ExceptionMessageConstants.UNABLE_TO_GET_TOKEN);
        }
        assert response != null;
        token = response.getAccessToken();
    }


    public BvnResponse verifyBvn(BvnRequest bvnRequest) throws IdentityException, InvalidArgumentException {
        if (StringUtils.isEmpty(bvnRequest.getBankVerificationNumber())) {
            throw new IdentityException(ExceptionMessageConstants.REQUIRED_FIELD_IS_EMPTY);
        }

        if (bvnRequest.getBankVerificationNumber().length() != 11) {
            throw new IdentityException(ExceptionMessageConstants.INVALID_BVN_LENGTH);
        }

        QoreIdBvnRequest ninRequest = QoreIdBvnRequest
                .builder()
                .firstname(bvnRequest.getFirstName())
                .lastname(bvnRequest.getLastName())
                .build();

        QoreIdBvnResponse bvnResponse = fetchQoreIdResponse(
                BASE_URL + "/v1/ng/identities/bvn-premium/" + bvnRequest.getBankVerificationNumber(),
                ninRequest,
                QoreIdBvnResponse.class
        );
        if (bvnResponse == null) {
            throw new IdentityException(ExceptionMessageConstants.ERROR_FETCHING_USER_INFORMATION);
        }
//        return QoreIdMapper.INSTANCE.qoreIdBvnResponseToBvnResponse(bvnResponse);
        return QoreIdDomainObject.convertQoreIdBvnResponseToBvnResponse(bvnResponse);
    }



    @Override
    public NinResponse verifyNin(NinRequest identityRequest) throws InvalidArgumentException, IdentityException {

        if (StringUtils.isEmpty(identityRequest.getNin()) || StringUtils.isBlank(identityRequest.getNin())){
            throw new IdentityException(ExceptionMessageConstants.REQUIRED_FIELD_IS_EMPTY);
        }
        QoreIdNinRequest ninRequest = QoreIdNinRequest
                .builder()
                .firstname(identityRequest.getFirstname())
                .lastname(identityRequest.getLastname())
                .phone(identityRequest.getPhonenumber())
                .build();

        QoreIdNinResponse ninResponse = fetchQoreIdResponse(BASE_URL + "/v1/ng/identities/nin/" + identityRequest.getNin(), ninRequest, QoreIdNinResponse.class
        );

        if (ninResponse == null) {
            throw new IdentityException(ExceptionMessageConstants.ERROR_FETCHING_USER_INFORMATION);
        }
        return QoreIdDomainObject.convertQoreIdINinResponseToNinResponse(ninResponse);
    }



    @Override
    public BusinessIdentityResponse verifyCompanyCacNumber(CacRequest businessRequest) throws IdentityException {

        if (businessRequest == null || businessRequest.getCompanyNumber() == null) {
            throw new IdentityException(ExceptionMessageConstants.REQUIRED_FIELD_IS_EMPTY);
        }
        QoreIdCacRequest newRequest = QoreIdCacRequest.builder().regNumber(businessRequest.getCompanyNumber()).build();

        QoreIdBusinessIdentityVerificationResponse newResponse = fetchQoreIdResponse(BASE_URL + "/v1/ng/identities/cac-premium", newRequest, QoreIdBusinessIdentityVerificationResponse.class
                );
        if (newResponse == null) {
            throw new IdentityException(ExceptionMessageConstants.ERROR_FETCHING_COMPANY_DETAILS);
        }
        return QoreIdDomainObject.convertBusinessVerificationResponseToBusinessResponse(newResponse);
    }


    private <T, R> R fetchQoreIdResponse(String url, T request, Class<R> responseType) {
        return restClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .retrieve()
                .onStatus(status -> status.value() == 404, (clientRequest, response) -> {
                    throw new RuntimeException(response.toString());
                })
                .body(responseType);
    }
}
