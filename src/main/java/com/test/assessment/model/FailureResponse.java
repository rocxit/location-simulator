package com.test.assessment.model;

public class FailureResponse {
    private final String errorCode;
    private final String errorDetail;
    private final String tag;

    public FailureResponse(final String errorCode,
                           final String errorDetail,
                           final String tag) {
        super();
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
        this.tag = tag;
    }


    public String getErrorCode() {
        return this.errorCode;
    }


    public String getErrorDetail() {
        return this.errorDetail;
    }


    public String getTag() {
        return this.tag;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FailureResponse{errorCode='")
                .append(errorCode)
                .append("', errorDetail='")
                .append(errorDetail)
                .append("', tag='")
                .append(tag)
                .append("'}");
        return builder.toString();
    }
}
