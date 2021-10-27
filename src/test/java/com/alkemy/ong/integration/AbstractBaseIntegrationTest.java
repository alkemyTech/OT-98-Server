package com.alkemy.ong.integration;

import com.alkemy.ong.repository.IUserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;

public class AbstractBaseIntegrationTest {

  protected TestRestTemplate restTemplate = new TestRestTemplate();
  protected HttpHeaders headers = new HttpHeaders();

  @MockBean
  protected IUserRepository userRepository;

  @MockBean
  protected AuthenticationManager authenticationManager;

  @LocalServerPort
  private int port;

  protected String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
