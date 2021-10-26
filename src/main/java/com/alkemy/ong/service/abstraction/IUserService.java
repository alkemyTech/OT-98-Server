package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.RequestUser;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface IUserService {

  User createUser(RequestUser requestUser) throws NoSuchElementException;

  Optional<User> findByEmail(String email);

}
