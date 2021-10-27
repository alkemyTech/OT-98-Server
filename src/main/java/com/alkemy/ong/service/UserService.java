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
  private BCryptPasswordEncoder passwordEncoder;

  public User validateUser(UserRequest toValidate) {
    User user = userRepository.findByEmail(toValidate.getEmail());

    if (user == null || !passwordEncoder.matches(toValidate.getPassword(), user.getPassword())) {
      return null;
    }

    return user;
  }

}
