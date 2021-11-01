package com.alkemy.ong.exception;

public class CategoryNameAlreadyExistException extends Exception {

  private final static String CATEGORY_NAME_ALREADY_EXISTS_EXCEPTION = "Category name already exist.";

  private static final long serialVersionUID = 1L;

  public CategoryNameAlreadyExistException() {
    super(CATEGORY_NAME_ALREADY_EXISTS_EXCEPTION);
  }

}
