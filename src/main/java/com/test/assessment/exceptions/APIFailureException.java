package com.test.assessment.exceptions;

import com.test.assessment.model.FailureResponse;
import org.springframework.http.HttpStatus;

public class APIFailureException extends RuntimeException {
    private final String errorCode;
    private final String errorDetail;
    private final HttpStatus httpStatusCode;

    APIFailureException(final String errorCode,
                        final String errorDetail,
                        final HttpStatus httpStatusCode) {
        super(errorDetail);

        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
        this.httpStatusCode = httpStatusCode;
    }


    public String getErrorCode() {
        return this.errorCode;
    }


    public String getErrorDetail() {
        return this.errorDetail;
    }


    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }


    public FailureResponse toFailureResponse(final String webRequestTag) {
        return new FailureResponse(this.errorCode, this.errorDetail, webRequestTag);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("APIFailureException [errorCode=")
                .append(this.errorCode)
                .append(", errorDetail=")
                .append(this.errorDetail)
                .append(", httpStatusCode=")
                .append(this.httpStatusCode)
                .append("]");
        return builder.toString();
    }
}
