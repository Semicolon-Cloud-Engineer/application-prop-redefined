package com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.customizedException;

import com.example.OnboardingIdentityManagementService.domain.exception.KarraboException;
import com.example.OnboardingIdentityManagementService.infrastructure.adapters.output.customizedException.data.response.KarraboExceptionResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class KarraboExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KarraboException.class)
    public ResponseEntity<KarraboExceptionResponse> exceptionHandler(KarraboException exception) {
        KarraboExceptionResponse response = KarraboExceptionResponse.builder()
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {

        Map<String, String> data = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            data.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        KarraboExceptionResponse response = KarraboExceptionResponse.builder()
                .data(data)
                .message("Bad request")
                .status(status)
                .build();

        return new ResponseEntity<>(response, status);
    }

}
