package com.alkemy.ong.exception;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.alkemy.ong.model.response.ErrorResponse;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
  public ResponseEntity<?> handleIOException(HttpServletRequest request, IOException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

}
