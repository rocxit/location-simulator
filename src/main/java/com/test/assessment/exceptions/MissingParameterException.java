package com.test.assessment.exceptions;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String errorMessage) {
        super(errorMessage);
    }
}
