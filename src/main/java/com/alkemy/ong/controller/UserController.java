package com.alkemy.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.UserServiceImpl;
import com.alkemy.ong.service.abstraction.IUserRegisterService;

@RestController
public class UserController {

  @Autowired
  public IUserRegisterService registerService;

  @Autowired
  public ConvertUtils convertUtils;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserServiceImpl userServiceImpl;

  @PostMapping(value = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest registerRequest)
      throws EmailAlreadyExistException {
    User user = registerService.register(registerRequest);
    UserRegisterResponse userRegisterResponse = convertUtils.toResponse(user);
    return new ResponseEntity<>(userRegisterResponse, HttpStatus.CREATED);
  }

  @GetMapping(value = "auth/me")
  public ResponseEntity<?> returnAuthenticatedUser(
      @RequestHeader(value = "Authorization") String authorizationHeader) {

    String username;

    username = jwtUtil.extractUsername(authorizationHeader);

    User user = (User) userServiceImpl.loadUserByUsername(username);

    UserAuthenticatedMeResponse userAuthenticatedMeResponse = new UserAuthenticatedMeResponse(
        user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoto());

    return new ResponseEntity<>(userAuthenticatedMeResponse, HttpStatus.ACCEPTED);

  }

}
