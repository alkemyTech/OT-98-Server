package com.alkemy.ong.exception;

import com.alkemy.ong.exception.response.GenericErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = OrganizationNotAcceptableException.class)
  public ResponseEntity<GenericErrorResponse> HandleOrganizationNotAcceptableException(OrganizationNotAcceptableException e){
    GenericErrorResponse genericErrorResponse = new GenericErrorResponse(e.getMessage(),
        HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now());
    return new ResponseEntity(genericErrorResponse, HttpStatus.NOT_ACCEPTABLE);
  }

}
