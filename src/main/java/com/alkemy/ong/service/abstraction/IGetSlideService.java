package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.model.entity.Slide;

public interface IGetSlideService {

  Slide getBy(Long id) throws EntityNotFoundException;

}
