package com.test.assessment.controller;

import com.test.assessment.exceptions.APIFailureException;
import com.test.assessment.exceptions.MissingParameterException;
import com.test.assessment.model.FailureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler extends org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler {

    @Autowired
    ServletaaRequestTagger servletRequestTagger;

    private ResponseEntity<Object> handleError(final Exception e, final WebRequest request, final FailureResponse errorDetail, final HttpStatus httpStatus) {
       return this.handleExceptionInternal(e, errorDetail, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(APIFailureException.class)
    protected ResponseEntity<Object> handleAPIFailureError(final APIFailureException ex, final WebRequest request) {
        return this.handleError(ex,
                request,
                ex.toFailureResponse(servletRequestTagger.getTag(request)),
                ex.getHttpStatusCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnexpectedError(final Exception ex, final WebRequest request) {
        return this.handleError(ex,
                request,
                new FailureResponse("serverError",
                        "Unexpected server error",
                        servletRequestTagger.getTag(request)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingParameterException.class)
    protected ResponseEntity<Object> handleMissingParaError(final Exception ex, final WebRequest request) {
        return this.handleError(ex,
                request,
                new FailureResponse("missingQueryPara",
                        ex.getMessage(),
                        servletRequestTagger.getTag(request)),
                HttpStatus.BAD_REQUEST);
    }

}
