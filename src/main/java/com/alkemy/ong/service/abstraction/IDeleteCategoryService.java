package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteCategoryService {

  void deleteBy(long id) throws EntityNotFoundException;

}
