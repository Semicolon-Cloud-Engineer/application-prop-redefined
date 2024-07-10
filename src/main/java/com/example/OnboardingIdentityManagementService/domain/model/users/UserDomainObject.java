package com.example.OnboardingIdentityManagementService.domain.model.users;


import com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.request.organization.InviteEmployeeRequest;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboBusinessParty;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.persistence.entity.KarraboPlatformUser;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDomainObject {

    private String userId;

    private KarraboBusinessParty businessParty;

    private String firstName;

    private String lastName;

    private String email;

    public KarraboPlatformUser toKarraboPlatformUserEntity() {
        return KarraboPlatformUser.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .businessParty(businessParty)
                .build();
    }

    public UserDomainObject(KarraboPlatformUser platformUser) {
        this.userId = platformUser.getUserId();
        this.firstName = platformUser.getFirstName();
        this.lastName = platformUser.getLastName();
        this.email = platformUser.getEmail();
        this.businessParty = platformUser.getBusinessParty();
    }

    public UserDomainObject(InviteEmployeeRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.email = request.getEmail();
    }

}
