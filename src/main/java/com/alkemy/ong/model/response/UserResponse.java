package com.alkemy.ong.model.response;

import lombok.Builder;

@Builder
public class UserResponse {

  private long id;
  private String email;
  private String firstName;
  private String lastName;
  private String password;
  private String photo;

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public String getPhoto() {
    return photo;
  }
}
