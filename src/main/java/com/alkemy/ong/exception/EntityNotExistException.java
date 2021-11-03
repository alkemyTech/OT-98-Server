package com.alkemy.ong.exception;

public class EntityNotExistException extends Exception {

  private static final long serialVersionUID = 1L;

  public EntityNotExistException(String message) {
    super(message);
  }
}
