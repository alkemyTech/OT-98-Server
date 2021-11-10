package com.alkemy.ong.exception;

public class NewsNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public NewsNotFoundException(String msg) {
    super(msg);
  }
}
