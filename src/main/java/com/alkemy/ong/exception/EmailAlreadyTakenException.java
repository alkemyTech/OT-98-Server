package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyTakenException extends AbstractGeneralException {

  private final String code;

  public EmailAlreadyTakenException(String code, String message) {
    super(message);
    this.code = code;
  }

  @Override
  public String getErrorCode() {
    return code;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
