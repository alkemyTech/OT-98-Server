package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.mail.EmailHelper;
import com.alkemy.ong.common.mail.template.RegisterTemplateEmail;
import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.common.validation.PasswordValidation;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.exception.EmailAlreadyExistException;
import com.alkemy.ong.exception.InvalidCredentialsException;
import com.alkemy.ong.exception.SendEmailException;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.model.response.ListActiveUsersResponse;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IAuthenticatedUserDetailsService;
import com.alkemy.ong.service.abstraction.IAuthenticationService;
import com.alkemy.ong.service.abstraction.IDeleteUserService;
import com.alkemy.ong.service.abstraction.IListUsersService;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import com.alkemy.ong.service.abstraction.IRoleService;
import com.alkemy.ong.service.abstraction.IUserRegisterService;
import com.alkemy.ong.service.abstraction.IUserUpdateService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements IAuthenticationService, UserDetailsService,
    IUserRegisterService, IAuthenticatedUserDetailsService, IListUsersService, IDeleteUserService,
    IUserUpdateService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IAuthenticatedUserDetailsService authenticatedUserDetails;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private IDeleteUserService deleteUserService;

  @Autowired
  private EmailHelper emailHelper;

  @Autowired
  private IOrganizationService organizationService;

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
    User user = userRepository.save(buildUser(registerRequest));
    sendEmail(registerRequest.getEmail());
    return convertUtils.toResponse(user, jwtUtil.generateToken(user));
  }

  private User buildUser(UserRegisterRequest userRegisterRequest) {
    User user = new User();
    user.setFirstName(userRegisterRequest.getFirstName());
    user.setLastName(userRegisterRequest.getLastName());
    user.setEmail(userRegisterRequest.getEmail());
    user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
    List<Role> roles = new ArrayList<>();
    roles.add(roleService.findBy(ApplicationRole.USER.getFullRoleName()));
    user.setRoles(roles);
    return user;
  }

  private void sendEmail(String email) {
    try {
      OrganizationResponse organizationDetails = organizationService.getOrganizationDetails();
      emailHelper.send(new RegisterTemplateEmail(
          email,
          organizationDetails.getImage(),
          organizationDetails.getName(),
          organizationDetails.getAddress(),
          organizationDetails.getPhone()
      ));
    } catch (SendEmailException e) {
      log.warn(e.getMessage());
    }
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
  public void delete(long id) throws EntityNotFoundException {
    User user = userRepository.getById(id);
    if (user == null) {
      throw new EntityNotFoundException("There's no User registered with that ID number!!!");
    }
    user.setSoftDeleted(true);
    userRepository.save(user);
  }

  @Override
  public UserDetailsResponse update(long id, UserUpdateRequest userUpdateRequest) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    }

    User user = mapFields(userUpdateRequest, optionalUser.get());
    userRepository.save(user);

    return UserDetailsResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .photo(user.getPhoto())
        .password(user.getPassword())
        .build();


  }

  private User mapFields(UserUpdateRequest userUpdateRequest, User user) {
    if (userUpdateRequest.getFirstName() != null) {
      user.setFirstName(userUpdateRequest.getFirstName());
    }
    if (userUpdateRequest.getLastName() != null) {
      user.setLastName(userUpdateRequest.getLastName());
    }
    if (userUpdateRequest.getEmail() != null) {
      user.setEmail(userUpdateRequest.getEmail());
    }
    if (userUpdateRequest.getPhoto() != null) {
      user.setPhoto(userUpdateRequest.getPhoto());
    }
    return user;
  }


}
