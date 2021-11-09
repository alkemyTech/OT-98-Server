package com.alkemy.ong.exception;

public class PageOutOfRangeException extends Exception {

  private static final long serialVersionUID = 1L;

  public PageOutOfRangeException(String message) {
    super(message);
  }
}
