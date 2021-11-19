package com.alkemy.ong.integration.news;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.repository.INewsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AbstractBaseNewsIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected INewsRepository newsRepository;

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

  protected List<News> stubNews(int count) {
    List<News> news = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      news.add(new News(i, "Example", "Example", "Example.png", stubNewsCategory(), null, false));
    }

    return news;
  }
}
