package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteSlide {

  void delete(long id) throws EntityNotFoundException;

}
