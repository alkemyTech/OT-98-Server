package com.alkemy.ong.exception;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.alkemy.ong.model.response.ExceptionResponse;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
  public ResponseEntity<?> IOExceptions(HttpServletRequest request, Exception e) {
    return ResponseEntity.badRequest().body(new ExceptionResponse(e, request.getRequestURI()));
  }

}
