package com.alkemy.ong.controller;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.RequestUser;
import com.alkemy.ong.model.response.ResponseUser;
import com.alkemy.ong.service.abstraction.IUserService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final IUserService iUserService;

  public UserController(IUserService iUserService) {
    this.iUserService = iUserService;
  }

  @PostMapping("/auth/register")
  public ResponseEntity<ResponseUser> registerUser(@Valid @RequestBody RequestUser requestUser) {
    if (iUserService.findByEmail(requestUser.getEmail()) != null) {
      try {
        throw new Exception("Email already taken");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    User user = iUserService.createUser(requestUser);
    ResponseUser responseUser = new ResponseUser();
    responseUser.setId(user.getId());
    responseUser.setFirstsName(user.getFirstName());
    responseUser.setLastName(user.getLastName());
    responseUser.setPhoto(user.getPhoto());
    responseUser.setTimestamp(user.getTimestamp());
    return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK);
  }

}
