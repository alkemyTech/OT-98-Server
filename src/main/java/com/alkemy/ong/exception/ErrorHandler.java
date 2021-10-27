package com.alkemy.ong.exception;

import com.alkemy.ong.exception.response.GenericErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = OrganizationNotFoundException.class)
  public ResponseEntity<GenericErrorResponse> handleOrganizationNotFoundException(
      OrganizationNotFoundException e){
    GenericErrorResponse genericErrorResponse = new GenericErrorResponse(e.getMessage(),
        HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    return new ResponseEntity(genericErrorResponse, HttpStatus.NOT_FOUND);
  }

}
