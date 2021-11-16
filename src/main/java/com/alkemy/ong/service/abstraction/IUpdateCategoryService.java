package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.request.CategoryUpdateRequest;
import com.alkemy.ong.model.response.DetailsCategoryResponse;

public interface IUpdateCategoryService {

  DetailsCategoryResponse update(CategoryUpdateRequest categoryUpdateRequest, long id)
      throws EntityAlreadyExistException;

}
