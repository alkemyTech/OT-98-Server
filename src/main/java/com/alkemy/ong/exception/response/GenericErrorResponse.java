package com.alkemy.ong.exception.response;

import java.time.LocalDateTime;

public class GenericErrorResponse {

  private String message;

  private int code;

  private LocalDateTime date;

  public GenericErrorResponse(String message, int code, LocalDateTime date) {
    this.message = message;
    this.code = code;
    this.date = date;
  }
}
