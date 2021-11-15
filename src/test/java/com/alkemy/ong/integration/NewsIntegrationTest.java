package com.alkemy.ong.integration;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


import java.sql.Timestamp;
import java.time.Instant;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.repository.INewsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  private INewsRepository newsRepository;

  @Test
  public void shouldReturnForbbidenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<NewsDetailsResponse> response = restTemplate.exchange(createURLWithPort("/news"),
        HttpMethod.POST, new HttpEntity<>(headers), NewsDetailsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenCategoryNewsNotExist() {
    when(newsRepository.findCategoryByName("news")).thenReturn(null);
    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest request = getNewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(request, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort("/news"),
        HttpMethod.POST, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "News category not found.");
  }

  @Test
  public void shouldCreateANewsSuccesfull() {
    when(newsRepository.findCategoryByName(eq("news"))).thenReturn(getNewsCategory());
    when(newsRepository.save(isA(News.class))).thenReturn(getNews());

    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest request = getNewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(request, headers);
    ResponseEntity<NewsDetailsResponse> response = restTemplate.exchange(createURLWithPort("/news"),
        HttpMethod.POST, entity, NewsDetailsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), getNews().getName());
  }

  private Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  private User stubUser(String role) {
    User user = new User();
    user.setEmail("klugo@alkemy.com");
    user.setPhoto("https://foo.jpg");
    user.setFirstName("Kevin");
    user.setLastName("Raimo Lugo");
    user.setPassword("foo12345");
    user.setRoles(Lists.list(stubRole(role)));
    user.setTimestamp(Timestamp.from(Instant.now()));
    user.setSoftDeleted(false);
    return user;
  }

  private void login(String role) {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("klugo@alkemy.com"))).thenReturn(stubUser(role));

    String jwt = SecurityTestConfig.createToken("klugo@alkemy.com", role);
    headers.set("Authorization", jwt);
    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(headers);
    restTemplate.exchange(createURLWithPort("/auth/me"), HttpMethod.GET, entity,
        UserAuthenticatedMeResponse.class);
  }

  private Category getNewsCategory() {
    return new Category(1L, "news", "Example", "Example", null, false);
  }

  private News getNews() {
    return new News(1l, "Example", "Example", "Example.png", getNewsCategory(), null, false);
  }

  private NewsDetailsRequest getNewsDetailsRequest() {
    NewsDetailsRequest newsDetailsRequest = new NewsDetailsRequest();
    newsDetailsRequest.setName("Example");
    newsDetailsRequest.setContent("Example");
    newsDetailsRequest.setImage("Example.png");
    return newsDetailsRequest;
  }
}
