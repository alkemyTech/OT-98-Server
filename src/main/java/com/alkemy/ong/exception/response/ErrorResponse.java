package com.alkemy.ong.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

  private String code;

  private String message;

  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
