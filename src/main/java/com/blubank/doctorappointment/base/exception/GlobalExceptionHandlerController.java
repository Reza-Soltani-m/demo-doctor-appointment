package com.blubank.doctorappointment.base.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    Map<String, String> map = ex.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, item->item.getDefaultMessage()));
    String message = map.entrySet()
            .stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .collect(Collectors.joining(", "));

    ErrorModel error = new ErrorModel(message);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorModel> handleConstraintViolationException(ConstraintViolationException ex) {
    var message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" ,"));
    ErrorModel error = new ErrorModel(message);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @Bean
  public ErrorAttributes errorAttributes() {
    // Hide exception field in the return object
    return new DefaultErrorAttributes() {
      @Override
      public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
          Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
          errorAttributes.put("locale", webRequest.getLocale()
                  .toString());
          errorAttributes.remove("path");

        return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults()
                .excluding(ErrorAttributeOptions.Include.EXCEPTION));
      }
    };
  }


  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ErrorModel> handleApplicationException(ApplicationException ex) {
    ErrorModel error = new ErrorModel(ex.getErrorMessage());
    return new ResponseEntity<>(error, ex.getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handle(Exception ex) {
    ErrorModel error = new ErrorModel("Internal Server Error");
    return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

