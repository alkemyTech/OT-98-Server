package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteUserService {

  void delete(long id) throws EntityNotFoundException;

}
