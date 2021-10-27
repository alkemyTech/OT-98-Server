package com.alkemy.ong.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.repository.IUserRepository;


@Service
public class UserService implements UserDetailsService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private AuthenticationManager authManager;

  public User login(UserAuthenticationRequest toValidate) throws EntityNotFoundException,
      AuthenticationException, InvalidCredentialsException, UsernameNotFoundException {

    if (!EmailValidation.isValid(toValidate.getEmail())
        || !PasswordValidation.isValid(toValidate.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    User user = userRepository.findByEmail(toValidate.getEmail());

    if (user == null) {
      throw new EntityNotFoundException("User not found");
    }

    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(toValidate.getEmail(), toValidate.getPassword()));

    this.loadUserByUsername(toValidate.getEmail());

    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    UserBuilder builder = null;

    if (user != null) {
      builder = org.springframework.security.core.userdetails.User.withUsername(username);
      builder.disabled(false);
      builder.password(user.getPassword());
      builder.authorities(getRoles());
    } else {
      throw new UsernameNotFoundException("Not Found User");
    }
    return builder.build();
  }


  private List<SimpleGrantedAuthority> getRoles() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    return authorities;
  }
}
