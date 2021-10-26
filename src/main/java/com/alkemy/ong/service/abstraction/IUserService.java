package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.RequestUser;
import java.util.NoSuchElementException;

public interface IUserService {

  User createUser(RequestUser requestUser) throws NoSuchElementException;

}
