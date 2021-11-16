package com.alkemy.ong.integration.news;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.integration.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.repository.INewsRepository;

public abstract class AbstractBaseNewsIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected INewsRepository newsRepository;

  protected Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  protected User stubUser(String role) {
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

  protected void login(String role) {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("klugo@alkemy.com"))).thenReturn(stubUser(role));

    String jwt = SecurityTestConfig.createToken("klugo@alkemy.com", role);
    headers.set("Authorization", jwt);
  }

  protected Category stubNewsCategory() {
    return new Category(1L, "news", "Example", "Example", null, false);
  }

  protected News stubNews() {
    return new News(1l, "Example", "Example", "Example.png", stubNewsCategory(), null, false);
  }

  protected NewsDetailsRequest stubNewsDetailsRequest() {
    NewsDetailsRequest newsDetailsRequest = new NewsDetailsRequest();
    newsDetailsRequest.setName("Example");
    newsDetailsRequest.setContent("Example");
    newsDetailsRequest.setImage("Example.png");
    return newsDetailsRequest;
  }

  protected List<News> stubNews(int n) {
    List<News> news = new ArrayList<News>();

    for (int i = 1; i <= n; i++) {
      news.add(new News(i, "Example", "Example", "Example.png", stubNewsCategory(), null, false));
    }

    return news;
  }
}
