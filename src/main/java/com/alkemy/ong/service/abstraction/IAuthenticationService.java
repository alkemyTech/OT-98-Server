package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;

public interface IAuthenticationService {

  User login(UserAuthenticationRequest authenticationRequest) throws InvalidCredentialsException;

}
