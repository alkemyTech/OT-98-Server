package com.alkemy.ong.exception;

import java.io.IOException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.alkemy.ong.model.response.ErrorResponse;

@ControllerAdvice
public class ErrorHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
  public ResponseEntity<?> handleIOException(HttpServletRequest request, IOException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(HttpServletRequest request,
      EntityNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> hanldeAuthenticationException(HttpServletRequest request,
      AuthenticationException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<?> hanldeInvalidCredentiaslException(HttpServletRequest request,
      InvalidCredentialsException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> hanldeUsernameNotFoundException(HttpServletRequest request,
      UsernameNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

}
