package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.ForbiddenException;

public interface IDeleteCommentsService {

  void delete(long id, String authorizationHeader) throws ForbiddenException;

}
