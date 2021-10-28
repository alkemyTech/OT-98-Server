package com.alkemy.ong.exception;

public class SendEmailException extends Exception {

  private static final long serialVersionUID = 1L;

  public SendEmailException(String message) {
    super(message);
  }
}
