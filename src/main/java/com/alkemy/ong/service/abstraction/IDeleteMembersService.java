package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteMembersService {

  void deleteBy(long id) throws EntityNotFoundException;

}
