package com.alkemy.ong.exception.handler;

import com.alkemy.ong.exception.AbstractGeneralException;
import com.alkemy.ong.exception.EmailAlreadyTakenException;
import com.alkemy.ong.model.response.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionGlobalHandler {

  @ExceptionHandler(EmailAlreadyTakenException.class)
  protected ResponseEntity<ErrorResponse> handleCustomException(
      AbstractGeneralException abstractGeneralException) {
    ErrorResponse response = new ErrorResponse();
    return ResponseEntity.status(abstractGeneralException.getStatus()).body(response);
  }
}
