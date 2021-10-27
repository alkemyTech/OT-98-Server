package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRequest;
import com.alkemy.ong.repository.IUserRepository;


@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User validateUser(UserRequest toValidate) {
    User user = userRepository.findByEmail(toValidate.getEmail());

    if (user == null) {
      return null;
    }

    if (bCryptPasswordEncoder.encode(toValidate.getPassword()).equals(user.getPassword())) {
      return user;
    }

    return null;


  }

}
