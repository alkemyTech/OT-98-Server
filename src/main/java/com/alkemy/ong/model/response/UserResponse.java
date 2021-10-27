package com.alkemy.ong.model.response;

import java.sql.Timestamp;
import java.util.List;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String photo;
  private List<Role> roles;
  private Timestamp timestamp;
  private boolean softDeleted;

  public UserResponse(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.photo = user.getPhoto();
    this.roles = user.getRoles();
    this.timestamp = user.getTimestamp();
    this.softDeleted = user.isSoftDeleted();
  }
}
