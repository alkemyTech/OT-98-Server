package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.RequestUser;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import com.alkemy.ong.service.abstraction.IUserService;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final IRoleService roleService;

  private final IUserRepository userRepository;

  public UserService(IRoleService roleService, IUserRepository userRepository) {
    this.roleService = roleService;
    this.userRepository = userRepository;
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
    user.setPhoto(requestUser.getPhoto());
    //encriptar password
    Role role = roleService.findRoleByName(requestUser.getRole());
    //set roles
    return user;
  }

}
