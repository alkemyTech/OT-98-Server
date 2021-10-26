package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> databaseConnection(Exception e){
    ResponseEntity response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND );
    return response;
  }

}
