package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.News;
import javax.persistence.EntityNotFoundException;

public interface IGetNewsService {

  News getBy(Long id) throws EntityNotFoundException;
}
