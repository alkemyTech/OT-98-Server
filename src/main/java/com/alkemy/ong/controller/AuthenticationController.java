package com.alkemy.ong.controller;

import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;
import com.alkemy.ong.service.abstraction.IAuthenticationService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping(value = "auth/login", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> validateUser(@RequestBody UserAuthenticationRequest userRequest)
      throws EntityNotFoundException, AuthenticationException, InvalidCredentialsException {
    User user = authenticationService.login(userRequest);
    return ResponseEntity.ok(
        new UserDetailsResponse(user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword(),
            user.getPhoto()));
  }
}
