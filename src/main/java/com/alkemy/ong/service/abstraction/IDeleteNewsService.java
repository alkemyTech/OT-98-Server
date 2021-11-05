package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.DeleteNewsResponse;

public interface IDeleteNewsService {

  DeleteNewsResponse deleteNews(long id);

}
