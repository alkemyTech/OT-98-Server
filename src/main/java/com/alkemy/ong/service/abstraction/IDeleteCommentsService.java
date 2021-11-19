package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.UnableToDeleteObjectException;

public interface IDeleteCommentsService {

  void delete(long id, String authorizationHeader) throws UnableToDeleteObjectException;

}
