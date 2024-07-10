package com.example.OnboardingIdentityManagementService.infrastructure.adapters.input.rest.data.response.karrabo;


import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponse<T> {

    private String message;

    private T body;

    private HttpStatus status;

}
