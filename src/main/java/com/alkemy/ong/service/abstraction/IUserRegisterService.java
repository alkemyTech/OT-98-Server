package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.UserRegisterResponse;

public interface IUserRegisterService {

  UserRegisterResponse register(UserRegisterRequest registerRequest)
      throws EmailAlreadyExistException;

}
