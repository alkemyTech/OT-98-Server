package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserResponse;
import com.alkemy.ong.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping(value = "auth/login")
  public ResponseEntity<?> validateUser(@RequestBody UserRequest userRequest) {

    User user = userService.validateUser(userRequest);

    if (user == null)
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Invalid email or password", HttpStatus.BAD_REQUEST.value()));

    return ResponseEntity.ok(new UserResponse(user));
  }
}
