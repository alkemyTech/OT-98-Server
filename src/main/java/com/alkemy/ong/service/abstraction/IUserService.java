package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;

public interface IUserService {

  User createUser(UserRegisterRequest requestUser);

  User findEmail(String email);

}
