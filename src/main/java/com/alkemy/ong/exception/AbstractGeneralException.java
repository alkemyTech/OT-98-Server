package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractGeneralException extends RuntimeException {

  protected AbstractGeneralException(String message) {
    super(message);
  }

  public abstract String getErrorCode();

  public abstract HttpStatus getStatus();

}
