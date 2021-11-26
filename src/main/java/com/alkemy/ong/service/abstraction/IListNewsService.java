package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.response.ListCommentsResponse;
import com.alkemy.ong.model.response.NewsDetailsCommentsResponse;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;

public interface IListNewsService {

  Page<News> list(int page, int size);
}
