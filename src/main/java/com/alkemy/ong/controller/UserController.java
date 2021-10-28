package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.abstraction.IUserRegisterService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  public IUserRegisterService userService;

  @Autowired
  public ConvertUtils convertUtils;

  @PostMapping(value = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> register(
      @Valid @RequestBody UserRegisterRequest requestUser) throws EmailAlreadyExistException {
    User user = userService.register(requestUser);
    UserRegisterResponse userRegisterResponse = convertUtils.toResponse(user);
    return new ResponseEntity<UserRegisterResponse>(userRegisterResponse, HttpStatus.CREATED);
  }

}
