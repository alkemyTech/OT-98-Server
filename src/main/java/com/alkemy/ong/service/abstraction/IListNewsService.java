package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import org.springframework.data.domain.Page;

public interface IListNewsService {

  Page<News> list(int page, int size);
}
