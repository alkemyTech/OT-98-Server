package com.alkemy.ong.model.response;

import lombok.Getter;

@Getter
public class ExceptionResponse {
  private String exception;
  private String message;
  private String path;

  public ExceptionResponse(Exception e, String path) {
    exception = e.getClass().getSimpleName();
    message = e.getMessage();
    this.path = path;
  }
}
