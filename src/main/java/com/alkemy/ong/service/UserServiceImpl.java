package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.ListActiveUsersResponse;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IAuthenticatedUserDetails;
import com.alkemy.ong.service.abstraction.IAuthenticationService;
import com.alkemy.ong.service.abstraction.IListUsersService;
import com.alkemy.ong.service.abstraction.IRoleService;
import com.alkemy.ong.service.abstraction.IUserRegisterService;
import com.alkemy.ong.service.abstraction.IDeleteUserService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IAuthenticationService, UserDetailsService,
    IUserRegisterService, IAuthenticatedUserDetails, IListUsersService, IDeleteUserService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  public IAuthenticatedUserDetails authenticatedUserDetails;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private ConvertUtils convertUtils;

  @Override
  public UserDetailsResponse login(UserAuthenticationRequest authenticationRequest)
      throws EntityNotFoundException, AuthenticationException, InvalidCredentialsException {

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

    return new UserDetailsResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getPhoto(),
        jwtUtil.generateToken(user));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(MessageFormat.format("User {0} not found.", username));
    }
    return user;
  }

  @Override
  @Transactional
  public UserRegisterResponse register(UserRegisterRequest registerRequest)
      throws EmailAlreadyExistException {
    if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
      throw new EmailAlreadyExistException();
    }

    User user = new User();
    user.setFirstName(registerRequest.getFirstName());
    user.setLastName(registerRequest.getLastName());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
    List<Role> roles = new ArrayList<>();
    roles.add(roleService.findBy(ApplicationRole.USER.getFullRoleName()));
    user.setRoles(roles);
    return convertUtils.toResponse(userRepository.save(user), jwtUtil.generateToken(user));
  }

  @Override
  public UserAuthenticatedMeResponse getUserDetails(String authorizationHeader) {
    String username = jwtUtil.extractUsername(authorizationHeader);
    User user = (User) this.loadUserByUsername(username);
    return new UserAuthenticatedMeResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPhoto());
  }


  @Override
  @Transactional
  public ListActiveUsersResponse listActiveUsers() {
    List<User> users = userRepository.findBySoftDeletedFalse();
    List<UserDetailsResponse> usersResponse = new ArrayList<>();

    for (User user : users) {
      usersResponse.add(UserDetailsResponse.builder()
          .id(user.getId())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .email(user.getEmail())
          .photo(user.getPhoto())
          .build());
    }
    return new ListActiveUsersResponse(usersResponse);
  }

  @Override
  public void deleteUserById(Long id) throws EntityNotFoundException {
    User user = userRepository.getById(id);
    if (user == null) throw new EntityNotFoundException("There's no User registered with that ID number!!!");
    userRepository.deleteUserById(id);
  }
}
