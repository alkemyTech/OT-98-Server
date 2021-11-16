package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("userDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  @JsonInclude(Include.NON_NULL)
  private String password;
  private String photo;
  @JsonInclude(Include.NON_NULL)
  private String jwt;
}
