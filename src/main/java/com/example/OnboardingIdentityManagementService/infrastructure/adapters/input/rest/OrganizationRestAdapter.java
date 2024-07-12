package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest;

import com.example.OnboardingIdentityManagementService.application.ports.input.InviteOrganizationUseCase;
import com.example.OnboardingIdentityManagementService.domain.exception.OrganizationException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteOrganizationRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.karrabo.ApiResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.organization.InviteOrganizationResponse;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.ResponseConstants;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.SwaggerUiConstants;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.messageConstants.UrlConstants;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstants.BASE_URL)
@RequiredArgsConstructor
public class OrganizationRestAdapter {

    private final InviteOrganizationUseCase inviteOrganizationUseCase;

    @PostMapping("/invite-organization")
    @Operation(summary = SwaggerUiConstants.INVITE_ORGANIZATION_TITLE,
            description = SwaggerUiConstants.INVITE_ORGANIZATION_DESCRIPTION)
    public ResponseEntity<ApiResponse<InviteOrganizationResponse>> inviteOrganization(@RequestBody @Valid InviteOrganizationRequest inviteOrganizationRequest) throws OrganizationException {
        InviteOrganizationResponse inviteResponse = inviteOrganizationUseCase.inviteOrganization(inviteOrganizationRequest);
        ApiResponse<InviteOrganizationResponse> response = new ApiResponse<>();
        response.setBody(inviteResponse);
        response.setMessage(ResponseConstants.SUCCESS_MESSAGE);
        response.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
