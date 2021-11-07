package com.alkemy.ong.controller;

import com.alkemy.ong.exception.InvalidCredentialsException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping(value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> login(@RequestBody UserAuthenticationRequest userRequest)
      throws EntityNotFoundException, AuthenticationException, InvalidCredentialsException {

    UserDetailsResponse user = authenticationService.login(userRequest);

    return ResponseEntity.ok(user);
  }

}
