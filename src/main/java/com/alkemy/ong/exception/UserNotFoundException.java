package com.alkemy.ong.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends Exception {
  private final static String USER_NOT_FOUND_EXCEPTION_MESSAGE = "user with id: {0} was not found.";

  private static final long serialVersionUID = 1L;

  public UserNotFoundException(Long userId) {
    super(MessageFormat.format(USER_NOT_FOUND_EXCEPTION_MESSAGE, userId));
  }
}
