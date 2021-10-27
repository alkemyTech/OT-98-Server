package com.alkemy.ong.exception.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class GenericErrorResponse {

  private String message;

  private HttpStatus httpStatus;

  private LocalDateTime date;

  public GenericErrorResponse(String message, HttpStatus httpStatus, LocalDateTime date) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.date = date;
  }
}
