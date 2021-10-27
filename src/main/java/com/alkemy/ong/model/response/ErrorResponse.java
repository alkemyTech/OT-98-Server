package com.alkemy.ong.model.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
  private String message;
  private int code;

  public ErrorResponse(Exception e, int code) {
    message = e.getMessage();
    this.code = code;
  }

  public ErrorResponse(String message, int code) {
    this.message = message;
    this.code = code;
  }
}
