package com.alkemy.ong.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.repository.IUserRepository;


@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authManager;

  public User login(UserAuthenticationRequest toValidate)
      throws EntityNotFoundException, AuthenticationException {
    User user = userRepository.findByEmail(toValidate.getEmail());

    if (user == null) {
      throw new EntityNotFoundException("User not found");
    }

    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(toValidate.getEmail(), toValidate.getPassword()));

    return user;
  }

}
