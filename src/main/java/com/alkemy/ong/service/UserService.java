package com.alkemy.ong.service;

import com.alkemy.ong.common.validation.CustomExceptionMessages;
import com.alkemy.ong.exception.EmailAlreadyTakenException;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import com.alkemy.ong.service.abstraction.IUserService;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IRoleService roleService;

  private final IUserRepository userRepository;

  public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(7);

  public UserService(IRoleService roleService, IUserRepository userRepository) {
    this.roleService = roleService;
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(UserRegisterRequest requestUser) throws EmailAlreadyTakenException {
    userRepository.findByEmail(requestUser.getEmail()).ifPresent(user -> {
      throw new EmailAlreadyTakenException(CustomExceptionMessages.EMAIL_ALREADY_TAKEN_CODE,
          CustomExceptionMessages.EMAIL_ALREADY_TAKEN_MESSAGE);
    });
    User user = new User();
    user.setFirstName(requestUser.getFirstName());
    user.setLastName(requestUser.getLastName());
    user.setEmail(requestUser.getEmail());
    user.setPassword(bCryptPasswordEncoder.encode(requestUser.getPassword()));
    user.getRoles().add(roleService.findRoleByName("ROLE_USER"));
    return user;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

}
