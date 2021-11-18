package com.alkemy.ong.exception;

import com.alkemy.ong.model.response.ErrorResponse;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(HttpServletRequest request,
      EntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(buildResponse(e, HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handleAuthenticationException(HttpServletRequest request,
      AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(buildResponse(e, HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<?> handleInvalidCredentialsException(HttpServletRequest request,
      InvalidCredentialsException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(EmailAlreadyExistException.class)
  public ResponseEntity<?> handleEmailAlreadyExist(HttpServletRequest request,
      EmailAlreadyExistException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(EntityAlreadyExistException.class)
  public ResponseEntity<?> handleEntityAlreadyExist(HttpServletRequest request,
      EntityAlreadyExistException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUsernameNotFoundException(HttpServletRequest request,
      UsernameNotFoundException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(HttpServletRequest request,
      BadCredentialsException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(buildResponse(e, HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {
    return ResponseEntity.internalServerError()
        .body(buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<?> handleIOException(HttpServletRequest request, IOException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(HttpServletRequest request,
      ConstraintViolationException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(SendEmailException.class)
  public ResponseEntity<?> handleSendEmailException(HttpServletRequest request,
      SendEmailException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<?> handleExternalServiceException(HttpServletRequest request,
      ExternalServiceException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(PageOutOfRangeException.class)
  public ResponseEntity<?> handlePageOutOfRangeException(HttpServletRequest request,
      PageOutOfRangeException e) {
    return ResponseEntity.badRequest()
        .body(buildResponse(e, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumenNotValidException(HttpServletRequest request,
      MethodArgumentNotValidException e) {

    List<FieldError> errorFields = e.getFieldErrors();
    String errorMessage = "";
    for (FieldError fieldError : errorFields) {
      String field = fieldError.getField();
      errorMessage += fieldError.getDefaultMessage().replaceAll("%s", field) + ".";

      if (errorFields.indexOf(fieldError) != e.getErrorCount() - 1) {
        errorMessage += " ";
      }
    }

    return ResponseEntity.badRequest()
        .body(buildResponse(errorMessage, HttpStatus.BAD_REQUEST));
  }

  private ErrorResponse buildResponse(Exception e, HttpStatus httpStatus) {
    return new ErrorResponse(e, httpStatus.value());
  }

  private ErrorResponse buildResponse(String message, HttpStatus httpStatus) {
    return new ErrorResponse(message, httpStatus.value());
  }
}
