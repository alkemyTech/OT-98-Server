package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;

public interface IAuthenticationService {

  UserDetailsResponse login(UserAuthenticationRequest authenticationRequest) throws InvalidCredentialsException;

}
