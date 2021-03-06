package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.messages.DocumentationMessages;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.abstraction.IAuthenticatedUserDetailsService;
import com.alkemy.ong.service.abstraction.IDeleteUserService;
import com.alkemy.ong.service.abstraction.IListUsersService;
import com.alkemy.ong.service.abstraction.IUserRegisterService;
import com.alkemy.ong.service.abstraction.IUserUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = DocumentationMessages.USER_CONTROLLER,
description = DocumentationMessages.USER_CONTROLLER_DESCRIPTION)
public class UserController {

  @Autowired
  public IUserRegisterService registerService;

  @Autowired
  public IAuthenticatedUserDetailsService authenticatedUserDetails;

  @Autowired
  public IListUsersService userService;

  @Autowired
  public IDeleteUserService deleteUserService;

  @Autowired
  public IUserUpdateService userUpdateService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(value = "/auth/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.AUTHENTICATION_CONTROLLER_LOGIN)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.USER_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "400",
          description = DocumentationMessages.USER_CONTROLLER_RESPONSE_400_DESCRIPTION)
  })
  public ResponseEntity<UserRegisterResponse> register(
      @Valid @RequestBody UserRegisterRequest registerRequest) throws EmailAlreadyExistException {
    return new ResponseEntity<>(registerService.register(registerRequest), HttpStatus.CREATED);
  }

  @GetMapping(value = "/auth/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.AUTHENTICATION_CONTROLLER_LOGIN)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.USER_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.USER_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
  public ResponseEntity<UserAuthenticatedMeResponse> getAuthenticatedUserDetails(
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    return new ResponseEntity<>(authenticatedUserDetails.getUserDetails(authorizationHeader),
        HttpStatus.OK);
  }

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> listActiveUsers() {
    return new ResponseEntity<>(userService.listActiveUsers(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity<?> deleteBy(@PathVariable Long id) throws EntityNotFoundException {
    deleteUserService.delete(id);
    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
  }

  @PatchMapping(value = "/users/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody
      UserUpdateRequest userUpdateRequest) throws EntityNotFoundException {
    UserDetailsResponse userDetailsResponse = userUpdateService.update(id, userUpdateRequest);
    return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
  }


}
