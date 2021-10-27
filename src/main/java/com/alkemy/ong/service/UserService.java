package com.alkemy.ong.service;

import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IAuthenticationService;
import java.text.MessageFormat;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IAuthenticationService, UserDetailsService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  public User login(UserAuthenticationRequest authenticationRequest) throws EntityNotFoundException,
      AuthenticationException, InvalidCredentialsException {

    if (!EmailValidation.isValid(authenticationRequest.getEmail())
        || !PasswordValidation.isValid(authenticationRequest.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password.");
    }

    User user = userRepository.findByEmail(authenticationRequest.getEmail());
    if (user == null) {
      throw new EntityNotFoundException("User not found.");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
            authenticationRequest.getPassword()));

    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          MessageFormat.format("User {0} not found.", username));
    }
    return user;
  }
}
