package com.alkemy.ong.exception;

public class EmailAlreadyExistException extends Exception {

  private final static String EMAIL_ALREADY_TAKEN_MESSAGE = "Email already taken";

  private static final long serialVersionUID = 1L;

  public EmailAlreadyExistException() {
    super(EMAIL_ALREADY_TAKEN_MESSAGE);
  }

}
