package com.alkemy.ong.exception;

public class InvalidCredentialsException extends Exception {
  private static final long serialVersionUID = 1L;

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
