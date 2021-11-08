package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;

public interface IGetNewsService {

  News getById(Long id);
}
