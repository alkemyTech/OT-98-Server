package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteUserService {
  void deleteUserById(Long id) throws EntityNotFoundException;
}
