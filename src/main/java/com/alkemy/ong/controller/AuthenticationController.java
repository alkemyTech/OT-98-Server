package com.alkemy.ong.controller;

import com.alkemy.ong.common.messages.DocumentationMessages;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;
import com.alkemy.ong.service.abstraction.IAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = DocumentationMessages.AUTHENTICATION_CONTROLLER,
  description = DocumentationMessages.AUTHENTICATION_CONTROLLER_DESCRIPTION)
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping(value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.AUTHENTICATION_CONTROLLER_LOGIN)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "400",
          description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_400_DESCRIPTION),
      @ApiResponse(responseCode = "401",
          description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_401_DESCRIPTION),
      @ApiResponse(responseCode = "404",
      description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_404_DESCRIPTION),
      @ApiResponse(responseCode = "500",
      description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_500_DESCRIPTION)
  })
  public ResponseEntity<?> login(@RequestBody UserAuthenticationRequest userRequest)
      throws EntityNotFoundException, AuthenticationException, InvalidCredentialsException {
    UserDetailsResponse user = authenticationService.login(userRequest);
    return ResponseEntity.ok(user);
  }

}
