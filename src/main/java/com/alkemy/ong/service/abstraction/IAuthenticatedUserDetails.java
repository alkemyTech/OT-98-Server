package com.alkemy.ong.service.abstraction;

import org.springframework.security.core.userdetails.UserDetails;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;

public interface IAuthenticatedUserDetails {
  UserDetails loadUserByUsername(String username);

  UserAuthenticatedMeResponse getUserDetails(String authorizationHeader);
}
