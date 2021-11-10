package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.ListCategoryResponse;
import java.util.List;

public interface IListCategoryService {

  List<ListCategoryResponse> findAll();

}
