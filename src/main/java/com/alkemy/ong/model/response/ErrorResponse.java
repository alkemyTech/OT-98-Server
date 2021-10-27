package com.alkemy.ong.model.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

  private final String message;
  private final int code;

  public ErrorResponse(Exception e, int code) {
    this(e.getMessage(), code);
  }

  public ErrorResponse(String message, int code) {
    this.message = message;
    this.code = code;
  }
}
