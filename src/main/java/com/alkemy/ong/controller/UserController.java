package com.alkemy.ong.controller;

import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.abstraction.IAuthenticatedUserDetails;
import com.alkemy.ong.service.abstraction.IUserRegisterService;
import com.alkemy.ong.service.abstraction.IDeleteUserService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.alkemy.ong.service.abstraction.IListUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  public IUserRegisterService registerService;

  @Autowired
  public IAuthenticatedUserDetails authenticatedUserDetails;

  @Autowired
  public IListUsersService userService;

  @Autowired
  public IDeleteUserService deleteUserService;

  @PostMapping(value = "/auth/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserRegisterResponse> register(
      @Valid @RequestBody UserRegisterRequest registerRequest) throws EmailAlreadyExistException {
    return new ResponseEntity<>(registerService.register(registerRequest), HttpStatus.CREATED);
  }

  @GetMapping(value = "/auth/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAuthenticatedUserDetails(
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    return new ResponseEntity<>(authenticatedUserDetails.getUserDetails(authorizationHeader),
        HttpStatus.OK);
  }

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listActiveUsers() {
    return new ResponseEntity<>(userService.listActiveUsers(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity<Long> deleteBy(@PathVariable Long id) throws EntityNotFoundException {
    deleteUserService.deleteUserById(id);
    return new  ResponseEntity<>(id, HttpStatus.NO_CONTENT);
  }

}
