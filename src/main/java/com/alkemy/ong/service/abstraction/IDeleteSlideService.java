package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteSlideService {

  void delete(long id) throws EntityNotFoundException;

}
