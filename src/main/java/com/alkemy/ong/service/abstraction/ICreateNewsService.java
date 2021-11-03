package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.CreateNewsRequest;

public interface ICreateNewsService {

  News create(CreateNewsRequest newsCreate);
}
