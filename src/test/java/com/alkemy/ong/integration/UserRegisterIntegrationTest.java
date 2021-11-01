package com.alkemy.ong.integration;

import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRegisterIntegrationTest extends AbstractBaseIntegrationTest {

  @Test
  public void ShouldReturnBadRequestWhenTheEmailAlredyExist() {
    when(userRepository.findByEmail("example@gmail.com")).thenReturn(new User());

    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setFirstName("Example");
    registerRequest.setLastName("Example");
    registerRequest.setEmail("example@gmail.com");
    registerRequest.setPassword("12346789");

    HttpEntity<UserRegisterRequest> entity = new HttpEntity<>(registerRequest, this.headers);

    ResponseEntity<ErrorResponse> response = this.restTemplate.exchange(
        createURLWithPort("/auth/register"), HttpMethod.POST, entity, ErrorResponse.class);

    Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assert.assertEquals("Email already exist.", response.getBody().getMessage());
  }
}
