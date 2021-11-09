package com.alkemy.ong.service.abstraction;

import org.springframework.data.domain.Page;
import com.alkemy.ong.model.entity.News;

public interface IListNewsService {

  Page<News> list(int page, int size);
}
