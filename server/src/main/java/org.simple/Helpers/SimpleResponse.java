package org.simple.Helpers;

public class SimpleResponse {
    private String message;
    private Boolean success;
    private String token;

    public SimpleResponse(String message, Boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }

    public SimpleResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public SimpleResponse() {
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }
}
