package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.EmailAlreadyTakenException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;

public interface IUserService {

  User createUser(UserRegisterRequest requestUser) throws EmailAlreadyTakenException;

  User findEmail(String email);

}
