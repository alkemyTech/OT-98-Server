package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;
import com.alkemy.ong.model.response.ListCommentsResponse;
import com.alkemy.ong.model.response.NewsDetailsCommentsResponse;

public interface IListCommentsService {

  ListCommentsResponse list();

  NewsDetailsCommentsResponse listNewsWithComments(long id) throws EntityNotFoundException;
}
