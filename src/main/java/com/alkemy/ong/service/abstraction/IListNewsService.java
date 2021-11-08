package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.ListNewsResponse;

public interface IListNewsService {

  ListNewsResponse list(int page, int size);
}
