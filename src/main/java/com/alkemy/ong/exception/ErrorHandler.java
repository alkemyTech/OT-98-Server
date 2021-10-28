package com.alkemy.ong.exception;

import com.alkemy.ong.model.response.ErrorResponse;
import java.io.IOException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(HttpServletRequest request,
      EntityNotFoundException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handleAuthenticationException(HttpServletRequest request,
      AuthenticationException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<?> handleInvalidCredentialsException(HttpServletRequest request,
      InvalidCredentialsException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUsernameNotFoundException(HttpServletRequest request,
      UsernameNotFoundException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(HttpServletRequest request,
      BadCredentialsException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<?> handleIOException(HttpServletRequest request, IOException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(SendEmailException.class)
  public ResponseEntity<?> handleSendEmailException(HttpServletRequest request,
      SendEmailException e) {
    return ResponseEntity.badRequest().body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  private ErrorResponse buildResponse(Exception e, HttpStatus httpStatus) {
    return new ErrorResponse(e, httpStatus.value());
  }

}
