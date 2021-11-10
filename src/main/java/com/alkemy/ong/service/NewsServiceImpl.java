package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.CreateNewsRequest;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.service.abstraction.ICreateNewsService;
import com.alkemy.ong.service.abstraction.IDeleteNewsService;
import com.alkemy.ong.service.abstraction.IGetNewsService;
import com.alkemy.ong.service.abstraction.IListNewsService;
import com.alkemy.ong.service.abstraction.IUpdateNewsService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements ICreateNewsService, IDeleteNewsService, IGetNewsService,
    IListNewsService, IUpdateNewsService {

  private static final String NEWS_CATEGORY = "news";

  @Autowired
  private INewsRepository newsRepository;

  @Override
  @Transactional
  public News create(CreateNewsRequest createNewsRequest) throws EntityNotFoundException {
    Category newsCategory = newsRepository.findCategoryByName(NEWS_CATEGORY);

    if (newsCategory == null) {
      throw new EntityNotFoundException("News category not found.");
    }

    News news = new News();
    news.setName(createNewsRequest.getName());
    news.setContent(createNewsRequest.getContent());
    news.setImage(createNewsRequest.getImage());
    news.setCategory(newsCategory);
    news.setSoftDelete(false);

    return newsRepository.save(news);
  }

  @Override
  @Transactional
  public void delete(long id) {
    News news = findByIdIfSoftDeleteIsFalse(id);
    news.setSoftDelete(true);
    newsRepository.save(news);
  }

  @Override
  @Transactional(readOnly = true)
  public News getBy(Long id) throws EntityNotFoundException {
    return findByIdIfSoftDeleteIsFalse(id);
  }

  @Override
  public Page<News> list(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return newsRepository.findBySoftDeleteIsFalse(pageable);
  }

  @Override
  @Transactional
  public News update(NewsDetailsRequest newsDetailsRequest, Long id) {
    News news = findByIdIfSoftDeleteIsFalse(id);

    news.setName(newsDetailsRequest.getName());
    news.setContent(newsDetailsRequest.getContent());
    news.setImage(newsDetailsRequest.getImage());

    return newsRepository.save(news);
  }

  @Transactional(readOnly = true)
  private News findByIdIfSoftDeleteIsFalse(Long id) throws EntityNotFoundException {
    Optional<News> news = newsRepository.findById(id);

    if (news.isEmpty() || news.get().isSoftDelete()) {
      throw new EntityNotFoundException("News not found!");
    }

    return news.get();
  }
}
