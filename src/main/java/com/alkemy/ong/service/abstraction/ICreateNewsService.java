package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import javax.persistence.EntityNotFoundException;

public interface ICreateNewsService {

  News create(NewsDetailsRequest createNewsRequest) throws EntityNotFoundException;

}
