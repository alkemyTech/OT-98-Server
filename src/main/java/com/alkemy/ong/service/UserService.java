package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.RequestUser;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import com.alkemy.ong.service.abstraction.IUserService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IRoleService roleService;

  private final IUserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserService(IRoleService roleService, IUserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.roleService = roleService;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public User createUser(RequestUser requestUser) throws NoSuchElementException {
    userRepository.findByEmail(requestUser.getEmail()).ifPresent(user -> {
      try {
        throw new Exception("Email already taken");
      } catch (Exception e) {
        e.printStackTrace();
      }
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
