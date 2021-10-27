package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private String message;
  private int code;

  public ErrorResponse(Exception e, int code) {
    this(e.getMessage(), code);
  }

  public ErrorResponse(String message, int code) {
    this.message = message;
    this.code = code;
  }
}
