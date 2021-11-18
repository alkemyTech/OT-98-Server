package com.alkemy.ong.service.abstraction;

public interface IDeleteCommentsService {

  boolean delete(long id, String authorizationHeader);

}
