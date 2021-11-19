package com.alkemy.ong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnableToDeleteObjectException extends RuntimeException {

  public UnableToDeleteObjectException(String message) {
    super(message);
  }

}
