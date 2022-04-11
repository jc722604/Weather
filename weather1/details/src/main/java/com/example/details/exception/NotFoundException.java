package com.example.details.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = -1L;
    private String errorCode;
    private String errorMessage;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NotFoundException(String message) {
        super(message);
    }
}
