package com.alkemy.ong.exception;

public class OrganizationNotFoundException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public OrganizationNotFoundException(String message){
    super(message);
  }

}
