package org.simple.Helpers;

public class SimpleResponse {
    private String message;
    private Boolean success;

    public SimpleResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
