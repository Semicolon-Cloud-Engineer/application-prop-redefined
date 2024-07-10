package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.customizedException.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KarraboExceptionResponse {

    private HttpStatus status;

    private String message;

    private Map<String, String> data;

}
