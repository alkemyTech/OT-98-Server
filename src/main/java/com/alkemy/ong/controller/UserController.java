package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserResponse;
import com.alkemy.ong.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping(value = "auth/login", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> validateUser(@RequestBody UserRequest userRequest) {

    if (!EmailValidation.isValid(userRequest.getEmail())
        || !PasswordValidation.isValid(userRequest.getPassword())) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Invalid email or password", HttpStatus.BAD_REQUEST.value()));
    }

    User user = userService.validateUser(userRequest);

    if (user == null)
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("Invalid credentials", HttpStatus.BAD_REQUEST.value()));

    return ResponseEntity.ok(new UserResponse(user));
  }
}
