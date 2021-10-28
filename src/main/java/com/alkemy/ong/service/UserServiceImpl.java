package com.alkemy.ong.service;

import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.config.JwtUtil;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IAuthenticationService, UserDetailsService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserDetailsResponse login(UserAuthenticationRequest authenticationRequest) throws EntityNotFoundException,
      AuthenticationException, InvalidCredentialsException {

    if (!EmailValidation.isValid(authenticationRequest.getEmail())
        || !PasswordValidation.isValid(authenticationRequest.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password.");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
            authenticationRequest.getPassword()));

    User user = (User) loadUserByUsername(authenticationRequest.getEmail());

    String jwt = "Bearer "+jwtUtil.generateToken(loadUserByUsername(authenticationRequest.getEmail()));
     userRepository.findByEmail(authenticationRequest.getEmail());
    return new UserDetailsResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getPhoto()
        ,jwt);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(
          MessageFormat.format("User {0} not found.", email));
    }
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return user;
  }
}
