package com.alkemy.ong.exception;

public class OrganizationNotAcceptableException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public OrganizationNotAcceptableException(String message){
    super(message);
  }

}
