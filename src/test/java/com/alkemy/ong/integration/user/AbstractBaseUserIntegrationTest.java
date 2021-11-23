package com.alkemy.ong.integration.user;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;

public class AbstractBaseUserIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected IUserRepository userRepository;

  protected UserUpdateRequest exampleUserRequest() {
    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setFirstName("John");
    userUpdateRequest.setLastName("Doe");
    userUpdateRequest.setEmail("johnny@gmail.com");
    userUpdateRequest.setPhoto("https://foo.jpg");
    return userUpdateRequest;
  }

  protected List<User> stubUser(int count) {
    List<User> users = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      users.add(
          stubUser(ApplicationRole.USER.getFullRoleName())
      );
    }
    return users;
  }

}
