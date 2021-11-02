package com.alkemy.ong.exception;

import java.text.MessageFormat;

public class EntityAlreadyExistException extends Exception {

  private final static String ALREADY_EXISTS_EXCEPTION_MESSAGE = "{0} already exist.";

  private static final long serialVersionUID = 1L;

  public EntityAlreadyExistException(String entityName) {
    super(MessageFormat.format(ALREADY_EXISTS_EXCEPTION_MESSAGE, entityName));
  }

}
