package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteUserService {
  void deleteById(Long id) throws EntityNotFoundException;
}
