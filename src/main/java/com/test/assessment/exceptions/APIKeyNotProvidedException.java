package com.test.assessment.exceptions;

import org.springframework.http.HttpStatus;

public class APIKeyNotProvidedException extends APIFailureException {
    public APIKeyNotProvidedException(String message) {
        super("apiKeyMissing", message, HttpStatus.UNAUTHORIZED);
    }
}
