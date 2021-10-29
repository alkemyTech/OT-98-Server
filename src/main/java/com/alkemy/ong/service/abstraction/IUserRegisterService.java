package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;

public interface IUserRegisterService {

  User register(UserRegisterRequest registerRequest) throws EmailAlreadyExistException;

}
