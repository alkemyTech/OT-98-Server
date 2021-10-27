package com.alkemy.ong.controller;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.abstraction.IUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  public IUserService userService;

  @PostMapping(value = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> registerUser(
      @Valid @RequestBody UserRegisterRequest requestUser) {
    User user = userService.createUser(requestUser);
    UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
    userRegisterResponse.setId(user.getId());
    userRegisterResponse.setFirstsName(user.getFirstName());
    userRegisterResponse.setLastName(user.getLastName());
    userRegisterResponse.setEmail(user.getEmail());
    return ResponseEntity.ok(userRegisterResponse);
  }

}
