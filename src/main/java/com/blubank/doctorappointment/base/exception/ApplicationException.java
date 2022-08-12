package com.blubank.doctorappointment.base.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  public ApplicationException(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;;

  public ApplicationException(String errorMessage, HttpStatus httpStatus) {
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
