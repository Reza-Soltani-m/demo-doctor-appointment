package com.blubank.doctorappointment.base.exception;

public class ErrorModel {

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    String errorMessage;
    int errorCode;

    public ErrorModel(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
