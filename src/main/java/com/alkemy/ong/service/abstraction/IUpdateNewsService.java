package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;

public interface IUpdateNewsService {

  News update(NewsDetailsRequest newsDetailsRequest, Long id);
}
